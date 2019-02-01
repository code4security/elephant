package com.sjhy.platform.service.dubbo;

import com.sjhy.platform.biz.bo.VerifySessionBO;
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.biz.deploy.exception.ChannelIDErrorException;
import com.sjhy.platform.client.service.VerifySessionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @HJ
 */
@Service(value = "VerifySessionService")
public class VerifySessionServiceImpl implements VerifySessionService {
    @Resource
    private VerifySessionBO verifySessionBO;

    @Override
    /**
     * 验证会话服务
     */
    public ResultDTO<Map<String, String>> verifySession(ServiceContext sc, String sessionId, String deviceUniqueID, int bindFlag, String subChannelId, String verifyId) {
        try {
            return ResultDTO.getSuccessResult(verifySessionBO.verifySession(sc, sessionId, deviceUniqueID, bindFlag, subChannelId, verifyId));
        } catch (ChannelIDErrorException e) {
            e.printStackTrace();
        }
        return null;
    }
}
