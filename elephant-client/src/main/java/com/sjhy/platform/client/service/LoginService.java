package com.sjhy.platform.client.service;
/**
 * @HJ
 */
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.dto.player.PlayerRole;
import com.sjhy.platform.client.dto.vo.ChannelAndVersionVO;
import com.sjhy.platform.client.dto.vo.LoginVO;
import com.sjhy.platform.client.dto.vo.RegularLoginVO;
import com.sjhy.platform.client.dto.vo.ReturnVo;

import java.math.BigInteger;

public interface LoginService {
    // 第一次握手
    ResultDTO<RegularLoginVO> loginChallenge(ServiceContext sc, int clientId, String deviceUniqueId, String channelName);

    // 第二次握手
    ResultDTO<RegularLoginVO> loginProof(ServiceContext sc, int clientId, String ip, BigInteger a, BigInteger m1 , String sdkVersion);

    // 验证是否需要进行游戏版本更新
    ResultDTO<ChannelAndVersionVO> checkChannelAndVersion(ServiceContext sc, String versionNum);

    // 确认登录服务器
    ResultDTO<LoginVO> confirmServer(ServiceContext sc, String ip, String activationCode);

    // 验证角色登陆，返回角色基本信息
    ResultDTO<ReturnVo> enterGame(ServiceContext sc, int deviceModel, String deviceToken);
}
