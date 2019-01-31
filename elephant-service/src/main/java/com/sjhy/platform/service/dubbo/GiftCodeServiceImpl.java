package com.sjhy.platform.service.dubbo;

import com.sjhy.platform.biz.bo.GiftCodeBO;
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.dto.exception.KairoException;
import com.sjhy.platform.client.dto.game.GiftCodeList;
import com.sjhy.platform.client.dto.history.PlayerGiftLog;
import com.sjhy.platform.client.service.GiftCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.management.relation.RoleNotFoundException;
import java.util.LinkedHashMap;

/**
 * @HJ
 */
@Service(value = "GiftCodeService")
public class GiftCodeServiceImpl implements GiftCodeService {

    @Resource
    private GiftCodeBO giftCodeBO;

    @Override
    /**
     * 查询是否使用过激活码
     */
    public ResultDTO<Boolean> isMeActivated(ServiceContext sc) {
        return ResultDTO.getSuccessResult(giftCodeBO.isMeActivated(sc));
    }

    @Override
    /**
     * 使用激活码
     */
    public ResultDTO<Integer> activateCode(ServiceContext sc, String code) {
        return ResultDTO.getSuccessResult(giftCodeBO.activateCode(sc,code));
    }

    @Override
    /**
     * 激活码是否有效，并未被使用过
     */
    public ResultDTO<GiftCodeList> isValidActivationCode(ServiceContext sc, String code) {
        return ResultDTO.getSuccessResult(giftCodeBO.isValidActivationCode(sc, code));
    }

    @Override
    /**
     * 判断兑换码是否已被使用
     */
    public ResultDTO<GiftCodeList> isValidRedeemCode(ServiceContext sc, String code) {
        return ResultDTO.getSuccessResult(giftCodeBO.isValidRedeemCode(sc,code));
    }

    @Override
    /**
     * 是否使用过同一批次礼品码
     */
    public ResultDTO<PlayerGiftLog> isUseForRedeemLot(ServiceContext sc, int giftListId) {
        return ResultDTO.getSuccessResult(giftCodeBO.isUseForRedeemLot(sc, giftListId));
    }

    @Override
    /**
     * 更新礼品码
     */
    public ResultDTO<Integer> redeemCode(ServiceContext sc, String code, int giftListId) {
        return ResultDTO.getSuccessResult(giftCodeBO.redeemCode(sc, code, giftListId));
    }

    @Override
    /**
     * 兑换码兑换逻辑
     */
    public ResultDTO<LinkedHashMap<Integer, Integer>> redeemCodeExchange(ServiceContext sc, String redeemCode) {
        try {
            return ResultDTO.getSuccessResult(giftCodeBO.redeemCodeExchange(sc, redeemCode));
        } catch (RoleNotFoundException e) {
            e.printStackTrace();
        } catch (KairoException e) {
            e.printStackTrace();
        }
        return null;
    }
}
