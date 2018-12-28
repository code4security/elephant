package com.sjhy.platform.client.service;
/**
 * @HJ
 */
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.dto.game.GiftCodeList;

public interface GiftCodeService {
    // 查询是否使用过激活码
    ResultDTO<Boolean> isMeActivated(ServiceContext sc);

    // 使用激活码
    ResultDTO<Integer> activateCode(ServiceContext sc, String code);

    // 激活码是否有效，并未被使用过
    ResultDTO<GiftCodeList> isValidActivationCode(ServiceContext sc, String code);
}
