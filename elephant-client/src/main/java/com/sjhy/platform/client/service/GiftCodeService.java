package com.sjhy.platform.client.service;
/**
 * @HJ
 */
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.dto.game.GiftCodeList;
import com.sjhy.platform.client.dto.history.PlayerGiftLog;

import java.util.LinkedHashMap;

public interface GiftCodeService {
    // 查询是否使用过激活码
    ResultDTO<Boolean> isMeActivated(ServiceContext sc);

    // 使用激活码
    ResultDTO<Integer> activateCode(ServiceContext sc, String code);

    // 激活码是否有效，并未被使用过
    ResultDTO<GiftCodeList> isValidActivationCode(ServiceContext sc, String code);

    // 判断兑换码是否已被使用
    ResultDTO<GiftCodeList> isValidRedeemCode(ServiceContext sc, String code);

    // 是否使用过同一批次礼品码
    ResultDTO<PlayerGiftLog> isUseForRedeemLot(ServiceContext sc, int giftListId);

    // 更新礼品码
    ResultDTO<Integer> redeemCode(ServiceContext sc, String code, int giftListId);

    // 兑换码兑换逻辑
    ResultDTO<LinkedHashMap<Integer, Integer>> redeemCodeExchange(ServiceContext sc, String redeemCode);
}
