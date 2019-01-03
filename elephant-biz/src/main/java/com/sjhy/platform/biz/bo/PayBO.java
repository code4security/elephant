package com.sjhy.platform.biz.bo;

import com.sjhy.platform.client.dto.fixed.VirtualCurrency;
import com.sjhy.platform.client.dto.config.KairoErrorCode;
import com.sjhy.platform.client.dto.exception.KairoException;
import com.sjhy.platform.client.dto.game.GiftCodeList;
import com.sjhy.platform.client.dto.utils.StringUtils;
import com.sjhy.platform.client.dto.vo.PlayerRoleVO;
import com.sjhy.platform.persist.mysql.fixed.VirtualCurrencyMapper;
import com.sjhy.platform.persist.mysql.player.PlayerRoleMapper;

import javax.annotation.Resource;
import javax.management.relation.RoleNotFoundException;
import java.util.LinkedHashMap;

public class PayBO {
    @Resource
    private PlayerRoleMapper playerRoleMapper;
    @Resource
    private GiftCodeBO giftCodeBO;
    @Resource
    private VirtualCurrencyMapper virtualCurrencyMapper;

    /**
     * 兑换礼品码兑换逻辑
     * @param roleId
     * @param redeemCode
     * @return
     * @throws RoleNotFoundException
     * @throws KairoException
     */
    public LinkedHashMap<Integer, Integer> redeemCodeExchange(long roleId, String gameId, String redeemCode) throws RoleNotFoundException, KairoException {
        // 玩家信息取得
        PlayerRoleVO role = (PlayerRoleVO) playerRoleMapper.selectByRoleId(gameId, roleId);
        if(role == null) {
            throw new RoleNotFoundException();
        }

        // 判断兑换码是否已被使用
        GiftCodeList giftCodeList = giftCodeBO.isValidRedeemCode(redeemCode, role.getChannelId(), role.getGameId(), roleId);
        if(giftCodeList == null){
            throw new KairoException(KairoErrorCode.ERROR_REDEEM_CODE);
        }

        if(StringUtils.isBlank(giftCodeList.getGiftRewardId())) {
            throw new KairoException(KairoErrorCode.ERROR_REDEEM_CODE);
        }

        // 检查同一批次是否已使用过
        if(giftCodeBO.isUseForRedeemLot(roleId, giftCodeList.getId(),gameId) != null) {
            throw new KairoException(KairoErrorCode.ERROR_REDEEM_CODE);
        }

        String goodsInfo = giftCodeList.getGiftRewardId();

        String[] goods = goodsInfo.split(",");
        String[] goodInfo = null;

        LinkedHashMap<Integer, Integer> goodsMap = new LinkedHashMap<Integer, Integer>();

        for(String good : goods) {
            goodInfo = good.split(":");

            if(goodInfo.length > 1) {
                // goodId是否有效
                VirtualCurrency virtualCurrency = virtualCurrencyMapper.selectByUnit(StringUtils.getString(goodInfo[0]));
                if(virtualCurrency != null) {
                    goodsMap.put(StringUtils.getInt(goodInfo[0]), StringUtils.getInt(goodInfo[1]));
                }
            }
        }

        // 更新兑换码
        giftCodeBO.redeemCode(redeemCode, role.getGameId(), role.getPlayerId(), roleId, role.getChannelId(), giftCodeList.getId());

        return goodsMap;
    }
}
