package com.sjhy.platform.test;

import com.sjhy.platform.biz.bo.LoginBO;
import com.sjhy.platform.biz.exception.*;
import com.sjhy.platform.biz.srp.SRPAuthenticationFailedException;
import com.sjhy.platform.biz.vo.RegularLoginVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigInteger;

/**
 * @HJ
 */
@RestController
public class LoginController {
    @Resource
    private LoginBO loginBO;

    @RequestMapping(value = "/one.do", method = RequestMethod.GET)
    public String loginChallenge(int clientId, String channelUserId, String deviceUniqueId, String channelId, String gameId) throws DeviceSignNullException,
            EmptyAccountNameException, AccountAlreadyBindingOtherException, NotExistAccountException {
        RegularLoginVO loginInfo = loginBO.loginChallenge(clientId,channelUserId,deviceUniqueId,channelId,gameId);
        return "s="+loginInfo.getSrp6Info().getSalt().toString(16)+"\n"
                + "B="+loginInfo.getSrp6Info().getB().toString(16);
    }

    @RequestMapping(value = "/two.do", method = RequestMethod.GET)
    public String loginProof(int clientId, String ip, BigInteger a, BigInteger m1 , String sdkVersion, int serverId, String gameId) throws NotChallengeYetException,
            SRPAuthenticationFailedException, KairoException {
        RegularLoginVO loginInfo = loginBO.loginProof(clientId,ip,a,m1,sdkVersion,serverId,gameId);
        return "M2="+loginInfo.getSrp6Info().getM2().toByteArray()+"\t"
                + "playerId="+loginInfo.getPlayerId()+"\t"
                + "Activation="+loginInfo.isNeedActivation();
    }
}