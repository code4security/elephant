package com.sjhy.platform.service.dubbo;

import com.sjhy.platform.biz.bo.LoginBO;
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.deploy.exception.*;
import com.sjhy.platform.client.dto.player.PlayerRole;
import com.sjhy.platform.client.deploy.srp.SRPAuthenticationFailedException;
import com.sjhy.platform.client.dto.vo.ChannelAndVersionVO;
import com.sjhy.platform.client.dto.vo.LoginVO;
import com.sjhy.platform.client.dto.vo.RegularLoginVO;
import com.sjhy.platform.client.dto.vo.ReturnVo;
import com.sjhy.platform.client.service.LoginService;

import javax.annotation.Resource;
import java.math.BigInteger;

/**
 * @HJ
 */
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = LoginService.class)
public class LoginServiceImpl implements LoginService {

    @Resource
    private LoginBO loginBO;

    @Override
    /**
     * 第一次握手
     */
    public ResultDTO<RegularLoginVO> loginChallenge(ServiceContext sc, int clientId, String deviceUniqueId, String channelName) {
        try {
            return ResultDTO.getSuccessResult(loginBO.loginChallenge(sc,clientId,deviceUniqueId,channelName));
        } catch (DeviceSignNullException e) {
            e.printStackTrace();
        } catch (EmptyAccountNameException e) {
            e.printStackTrace();
        } catch (AccountAlreadyBindingOtherException e) {
            e.printStackTrace();
        } catch (NotExistAccountException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * 第二次握手
     */
    public ResultDTO<RegularLoginVO> loginProof(ServiceContext sc, int clientId, String ip, BigInteger a, BigInteger m1, String sdkVersion) {
        try {
            return ResultDTO.getSuccessResult(loginBO.loginProof(sc,clientId,ip,a,m1,sdkVersion));
        } catch (NotChallengeYetException e) {
            e.printStackTrace();
        } catch (SRPAuthenticationFailedException e) {
            e.printStackTrace();
        } catch (KairoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * 验证是否需要进行游戏版本更新
     */
    public ResultDTO<ChannelAndVersionVO> checkChannelAndVersion(ServiceContext sc, String versionNum) {
        return ResultDTO.getSuccessResult(loginBO.checkChannelAndVersion(sc,versionNum));
    }

    @Override
    /**
     * 确认登录服务器
     */
    public ResultDTO<LoginVO> confirmServer(ServiceContext sc, String ip, String activationCode) {
        try {
            return ResultDTO.getSuccessResult(loginBO.confirmServer(sc,ip,activationCode));
        } catch (FreezeTheAccountException e) {
            e.printStackTrace();
        } catch (NotChallengeYetException e) {
            e.printStackTrace();
        } catch (NotExistAccountException e) {
            e.printStackTrace();
        } catch (IpIsNotInWhiteListException e) {
            e.printStackTrace();
        } catch (ActivationCodeIsNotRightException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * 验证角色登陆，返回角色基本信息
     */
    public ResultDTO<ReturnVo> enterGame(ServiceContext sc, int deviceModel, String deviceToken) {
        try {
            return ResultDTO.getSuccessResult(loginBO.enterGame(sc,deviceModel,deviceToken));
        } catch (PleaseLoginAgainException e) {
            e.printStackTrace();
        } catch (NoSuchRoleException e) {
            e.printStackTrace();
        } catch (AlreadyExistsPlayerRoleException e) {
            e.printStackTrace();
        } catch (CreateRoleException e) {
            e.printStackTrace();
        } catch (GameIdIsNotExsitsException e) {
            e.printStackTrace();
        } catch (KairoException e) {
            e.printStackTrace();
        }
        return null;
    }
}
