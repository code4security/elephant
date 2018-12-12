package com.sjhy.platform.biz.player;

import com.sjhy.platform.client.dto.base.AccountVO;
import com.sjhy.platform.client.dto.base.LoginSessionVO;
import com.sjhy.platform.client.dto.base.RegularLoginVO;
import com.sjhy.platform.client.exception.AccountAlreadyBindingOtherException;
import com.sjhy.platform.client.exception.DeviceSignNullException;
import com.sjhy.platform.client.exception.EmptyAccountNameException;
import com.sjhy.platform.client.exception.NotExistAccountException;
import com.sjhy.platform.client.srp.SRPFactory;
import com.sjhy.platform.client.srp.SRPVerifier;
import com.sjhy.platform.client.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @HJ
 */
@Service
public class LoginBO {

    private static final Logger logger = Logger.getLogger( LoginBO.class );
    @Resource
    private PlayerBO playerBO;

    public RegularLoginVO loginChallenge(int clientId, String channelUserID, String deviceUniqueID, String cooperate, String pmtChannelId, String gameId) throws DeviceSignNullException,
            EmptyAccountNameException, AccountAlreadyBindingOtherException, NotExistAccountException
    {
        logger.error("LoginService|======================>loginChallenge");

        // 判断是否是空的账号名或是空的设备唯一标识
        if (StringUtils.isBlank( channelUserID ) ||  StringUtils.isBlank( deviceUniqueID )
                ||  StringUtils.isBlank( cooperate ))
            throw new EmptyAccountNameException();

        logger.error("loginChallenge|deviceUniqueID="+deviceUniqueID);

        // 获取账户信息
        AccountVO account = playerBO.getAccount(pmtChannelId, channelUserID, deviceUniqueID, cooperate ,gameId);
        RegularLoginVO result = new RegularLoginVO();

        // 产生校验器
        logger.error("loginChallenge|"+account.getPassword());

        SRPVerifier verifier = SRPFactory.getInstance().makeVerifier( ( account.getPassword() ).getBytes() );

        // 生成加密验证session
        LoginSessionVO sessionVO = new LoginSessionVO();
        sessionVO.Session = SRPFactory.getInstance().newServerSession( verifier );

        //缓存

        // 构造结果
        result.getSrp6Info().setSalt( verifier.salt_s );
        result.getSrp6Info().setB( sessionVO.Session.getPublicKey_B() );

        // 埋点
        // logService.setMdEventLog(deviceUniqueID, account.getChUserId(), account.getId().toString(), account.getChannelId(), gameId, "loginsessionone_server");

        // 返回结果
        return result;
    }
}
