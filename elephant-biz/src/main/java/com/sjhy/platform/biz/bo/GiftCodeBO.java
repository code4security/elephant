package com.sjhy.platform.biz.bo;

import com.sjhy.platform.client.dto.config.KairoErrorCode;
import com.sjhy.platform.client.dto.exception.KairoException;
import com.sjhy.platform.client.dto.fixed.VirtualCurrency;
import com.sjhy.platform.client.dto.utils.StringUtils;
import com.sjhy.platform.client.dto.utils.UtilDate;
import com.sjhy.platform.client.dto.fixed.GiftCode;
import com.sjhy.platform.client.dto.game.GiftCodeList;
import com.sjhy.platform.client.dto.history.PlayerGiftLog;
import com.sjhy.platform.client.dto.vo.PlayerRoleVO;
import com.sjhy.platform.persist.mysql.fixed.GiftCodeMapper;
import com.sjhy.platform.persist.mysql.fixed.VirtualCurrencyMapper;
import com.sjhy.platform.persist.mysql.game.GiftCodeListMapper;
import com.sjhy.platform.persist.mysql.history.PlayerGiftLogMapper;
import com.sjhy.platform.persist.mysql.player.PlayerRoleMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.management.relation.RoleNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @HJ
 */
@Service
public class GiftCodeBO {
    private static final Logger logger = Logger.getLogger( GiftCodeBO.class );
    @Resource
    private PlayerGiftLogMapper playerGiftLogMapper;
    @Resource
    private GiftCodeMapper giftCodeMapper;
    @Resource
    private GiftCodeListMapper giftCodeListMapper;
    @Resource
    private PlayerRoleMapper playerRoleMapper;
    @Resource
    private GiftCodeBO giftCodeBO;
    @Resource
    private VirtualCurrencyMapper virtualCurrencyMapper;

    /**
     * 查询是否使用过激活码
     * @param gameId
     * @param playerId
     * @return
     */
    public boolean isMeActivated(String gameId, long playerId){
        // 是否使用过激活码
        PlayerGiftLog playerGiftLog = new PlayerGiftLog();
        playerGiftLog.setGameId(gameId);
        playerGiftLog.setPlayerId(playerId);
        // 查询
        PlayerGiftLog lipinmaLog = playerGiftLogMapper.selectByUseGiftCode(playerGiftLog);
        if(lipinmaLog != null){
            return true;
        }
        return false;
    }

    /**
     * 使用激活码
     * @param code
     * @param gameId
     * @param playerId
     * @param channelId
     * @return
     */
    public int activateCode(String code, String gameId, long playerId, String channelId) {
        // 激活码是否有效
        GiftCodeList giftCodeList = isValidActivationCode(code, channelId, gameId, playerId);
        if(giftCodeList == null){
            return -1;
        }

        PlayerGiftLog playerGiftLog = new PlayerGiftLog();

        playerGiftLog.setGiftCode(code);
        playerGiftLog.setPlayerId(playerId);
        playerGiftLog.setGameId(gameId);
        playerGiftLog.setChannelId(channelId);
        playerGiftLog.setActivateTime(new Date());
        playerGiftLog.setGiftListId(giftCodeList.getId());

        return playerGiftLogMapper.insert(playerGiftLog);
    }

    /**
     * 激活码是否有效，并未被使用过
     * @param code
     * @param channelId
     * @param gameId
     * @param playerId
     * @return
     */
    public GiftCodeList isValidActivationCode(String code, String channelId, String gameId, long playerId) {
        // 码信息取得
        GiftCode giftCode = giftCodeMapper.selectByCode(code);
        if(giftCode == null){
            return null;
        }

        // 码种类取得
        GiftCodeList maType = giftCodeListMapper.selectByPrimaryKey(giftCode.getGiftListId());
        if(maType == null){
            return null;
        }

        if(maType.getType() == 0){
            // 判断是否在使用时间内
            Date begin = UtilDate.text2Date(maType.getBeginTime());
            Date end = UtilDate.text2Date(maType.getEndTime());

            if(begin.getTime() > Calendar.getInstance().getTime().getTime() || end.getTime() < Calendar.getInstance().getTime().getTime()){
                //logger.error("兑换码已过期|code="+code);
                return null;
            }

            if(gameId != maType.getGameId()){
                //logger.error("不同游戏不能使用|code="+code);
                return null;
            }

            if(!channelId.equals(maType.getChannelId())){
                //logger.error("不同渠道不能使用|code="+code);
                return null;
            }

            // 是否被使用过
            PlayerGiftLog playerGiftLog = new PlayerGiftLog();
            playerGiftLog.setGameId(gameId);
            playerGiftLog.setGiftCode(code);

            PlayerGiftLog lipinmaLog = playerGiftLogMapper.selectByGiftCode(playerGiftLog);
            if(playerGiftLog != null){
                //logger.error("游戏已使用|code="+code);
                return null;
            }
            return maType;
        }
        return null;
    }

    /**
     * 判断兑换码是否已被使用
     * @param code
     * @param channelId
     * @param gameId
     * @param roleId
     * @return
     */
    public GiftCodeList isValidRedeemCode(String code, String channelId, String gameId, long roleId) {
        // 码信息取得
        GiftCode giftCode = giftCodeMapper.selectByCode(code);
        if(giftCode == null){
            return null;
        }

        // 码种类取得
        GiftCodeList maType = giftCodeListMapper.selectByPrimaryKey(giftCode.getGiftListId());
        if(maType == null){
            return null;
        }

        if(maType.getType() == 1){
            // 判断是否在使用时间内
            Date begin = UtilDate.text2Date(maType.getBeginTime());
            Date end   = UtilDate.text2Date(maType.getEndTime());

            if(begin.getTime() > Calendar.getInstance().getTime().getTime() || end.getTime() < Calendar.getInstance().getTime().getTime()){
                return null;
            }

            if(gameId != maType.getGameId()){
                logger.error("不同游戏不能使用|code="+code);
                return null;
            }

            if(!channelId.equals(maType.getChannelId())){
                logger.error("不同渠道不能使用|code="+code);
                return null;
            }

            // 检查激活码是否被使用过
            PlayerGiftLog playerGiftLog = new PlayerGiftLog();
            playerGiftLog.setGameId(gameId);
            playerGiftLog.setGiftCode(code);
            playerGiftLog = playerGiftLogMapper.selectByGiftCode(playerGiftLog);
            if(playerGiftLog != null){
                logger.error("游戏已使用|code="+code);
                return null;
            }

            return maType;
        } else if(maType.getType() == 2) {// 通用兑换码
            // 判断是否在使用时间内
            Date begin = UtilDate.text2Date(maType.getBeginTime());
            Date end   = UtilDate.text2Date(maType.getEndTime());

            if(begin.getTime() > Calendar.getInstance().getTime().getTime() || end.getTime() < Calendar.getInstance().getTime().getTime()){
                return null;
            }

            if(gameId != maType.getGameId()){
                logger.error("不同游戏不能使用|code="+code);
                return null;
            }

            // 检查是否使用过激活码
            if(this.isUseForRedeemLot(roleId,giftCode.getGiftListId(),gameId) != null){
                logger.error("游戏已使用|code="+code);
                return null;
            }
            return maType;
        }
        return null;
    }

    /**
     * 是否使用过同一批次礼品码
     * @param gameId
     * @param roleId
     * @param giftListId
     * @return
     */
    public PlayerGiftLog isUseForRedeemLot(long roleId, int giftListId, String gameId) {
        // 是否使用过同一批次礼品码
        PlayerGiftLog playerGiftLog = new PlayerGiftLog();
        playerGiftLog.setGiftListId(giftListId);
        playerGiftLog.setRoleId(roleId);
        playerGiftLog.setGameId(gameId);

        return playerGiftLogMapper.selectByUseGiftCode(playerGiftLog);
    }

    /**
     * 更新礼品码
     * @param code
     * @param gameId
     * @param playerId
     * @param roleId
     * @param channelId
     * @param giftListId
     * @return
     */
    public int redeemCode(String code, String gameId, long playerId, long roleId, String channelId, int giftListId) {
        PlayerGiftLog playerGiftLog = new PlayerGiftLog();

        playerGiftLog.setGiftCode(code);
        playerGiftLog.setRoleId(roleId);
        playerGiftLog.setPlayerId(playerId);
        playerGiftLog.setGameId(gameId);
        playerGiftLog.setChannelId(channelId);
        playerGiftLog.setActivateTime(new Date());
        playerGiftLog.setGiftListId(giftListId);

        return playerGiftLogMapper.insert(playerGiftLog);
    }

    /**
     * 兑换码兑换逻辑
     * @param roleId
     * @param redeemCode
     * @throws RoleNotFoundException
     * @throws KairoException
     */
    public LinkedHashMap<Integer, Integer> redeemCodeExchange(long roleId, String redeemCode, String gameId) throws RoleNotFoundException, KairoException {
        // 玩家信息取得
        PlayerRoleVO role = (PlayerRoleVO) playerRoleMapper.selectByRoleId(gameId,roleId);
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
        if(giftCodeBO.isUseForRedeemLot(roleId, giftCodeList.getId(), gameId) != null) {
            throw new KairoException(KairoErrorCode.ERROR_REDEEM_CODE);
        }

        String goodsInfo  = giftCodeList.getGiftRewardId();

        String[] goods    = goodsInfo.split(",");
        String[] goodInfo = null;

        LinkedHashMap<Integer, Integer> goodsMap = new LinkedHashMap<Integer, Integer>();

        for(String good : goods) {
            goodInfo = good.split(":");

            if(goodInfo.length > 1) {
                // goodId是否有效
                VirtualCurrency unit = virtualCurrencyMapper.selectByUnit(goodInfo[0]);
                if(unit != null) {
                    goodsMap.put(StringUtils.getInt(goodInfo[0]), StringUtils.getInt(goodInfo[1]));
                }
            }
        }

        // 更新兑换码
        giftCodeBO.redeemCode(redeemCode, role.getGameId(), role.getPlayerId(), roleId, role.getChannelId(), giftCodeList.getId());

        return goodsMap;
    }
}
