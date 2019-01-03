package com.sjhy.platform.biz.bo;

import com.sjhy.platform.client.dto.exception.ChannelIDErrorException;
import com.sjhy.platform.client.dto.game.ChannelAndVersion;
import com.sjhy.platform.persist.mysql.game.ChannelAndVersionMapper;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public class VerifySessionBO {
    private static Logger logger = Logger.getLogger(VerifySessionBO.class);
    @Resource
    private ChannelAndVersionMapper channelAndVersionMapper;

    /**
     * 验证会话服务
     * @param gameId
     * @param channelId
     * @param sessionId
     * @param deviceUniqueID
     * @param bindFlag
     * @param pmtChannelId
     * @param verifyId
     * @return
     * @throws ChannelIDErrorException
     */
    public Map<String, String> verifySession(String gameId, String channelId, String sessionId, String deviceUniqueID, int bindFlag, String pmtChannelId, String verifyId) throws ChannelIDErrorException
    {
        logger.error("verifySession|" + "渠道ID="+channelId+"|verifyId="+verifyId);
        // 第三方用户Id
        String channelUserId = "";
        // 定义返回结构
        Map<String, String> ret = initVerifyResultMap();
        if(bindFlag == 0) {// 不绑定，验证
            if(channelId.equals("shijun_in") || channelId.equals("1")){// 游客登录
                ret.put("cuid", verifyId);
            }else{
                // 第三方登录&验证
                channelUserId = verify(gameId, channelId, sessionId, pmtChannelId, verifyId);
                ret.put("cuid", channelUserId);
            }
        } else {
            // 第三方登录&验证
            channelUserId = verify(gameId, channelId, sessionId, pmtChannelId, verifyId);
            ret.put("cuid", channelUserId);
        }
        // 缓存（注释）
        //logService.setMdEventLog(deviceUniqueID, userId, "", channelId, gameId, "reqlogin_server");
        return ret;
    }

    private Map<String, String> initVerifyResultMap(){
        Map<String, String> retMap = new HashMap<String, String>();

        retMap.put("cuid", "");
        retMap.put("fPlayerId", "");
        retMap.put("fDeviceId", "");
        retMap.put("fTypeId", "");

        retMap.put("dTypeId", "");
        retMap.put("dPlayerId", "");

        return retMap;
    }

    private String verify(String gameId, String channelId, String sessionId, String subChannelId, String verifyId) throws ChannelIDErrorException
    {

        ChannelAndVersion channelAndVersion = new ChannelAndVersion();
        channelAndVersion.setChannelId(channelId);
        channelAndVersion.setGameId(gameId);
        channelAndVersion = channelAndVersionMapper.verifyChannel(channelAndVersion);
        // 验证渠道是否存在
        if (channelAndVersion == null){
            logger.error("渠道验证错误。。。，请检查渠道["+channelId+"]配置");

            throw new ChannelIDErrorException();
        }
        Map<String, Object> extraParams = new HashMap<String, Object>();
        extraParams.put("verifyId", verifyId);
        extraParams.put("subChannelId", subChannelId);
        extraParams.put("gameId", gameId);
        // 注释
        // String channelUserId = verifySession.verify(channelId, sessionId, extraParams);
        // return channelUserId;
        return "";
    }
}
