package com.sjhy.platform.biz.bo;

import com.sjhy.platform.biz.verify.IVerifySession;
import com.sjhy.platform.client.deploy.exception.ChannelIDErrorException;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.dto.game.ChannelAndVersion;
import com.sjhy.platform.client.dto.game.GameChannelSetting;
import com.sjhy.platform.persist.mysql.game.ChannelAndVersionMapper;

import com.sjhy.platform.persist.mysql.game.GameChannelSettingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VerifySessionBO {
    private static final Logger logger = LoggerFactory.getLogger(VerifySessionBO.class);
    @Autowired
    private ChannelAndVersionMapper channelAndVersionMapper;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private GameChannelSettingMapper gameChannelSettingMapper;

    private static Map<String, GameChannelSetting> C_Game_Channel_Setting = new HashMap<String, GameChannelSetting>();

    @PostConstruct
    public void start(){
        List<GameChannelSetting> all = gameChannelSettingMapper.selectByAll(new GameChannelSetting());

        if(all != null && all.size() > 0){
            for(GameChannelSetting val : all){
                if(val.getSubChannelId() != null){
                    C_Game_Channel_Setting.put(val.getGameId() + "_" + val.getSubChannelId(), val);
                }else{
                    C_Game_Channel_Setting.put(val.getGameId() + "_" + val.getChannelId(), val);
                }
            }
        }
    }

    /**
     * 验证会话服务
     * @param sessionId
     * @param deviceUniqueID
     * @param bindFlag
     * @param subChannelId
     * @param verifyId
     * @return
     * @throws ChannelIDErrorException
     */
    public Map<String, String> verifySession(ServiceContext sc, String sessionId, String deviceUniqueID, int bindFlag, String subChannelId, String verifyId) throws ChannelIDErrorException
    {
        logger.error("verifySession|" + "渠道ID="+sc.getChannelId()+"|verifyId="+verifyId);
        // 第三方用户Id
        String channelUserId = "";
        // 定义返回结构
        Map<String, String> ret = initVerifyResultMap();
        if(bindFlag == 0) {// 不绑定，验证
            if(sc.getChannelId().equals("shijun_in") || sc.getChannelId().equals("1")){// 游客登录
                ret.put("cuid", verifyId);
            }else{
                // 第三方登录&验证
                channelUserId = verify(sc.getGameId(), sc.getChannelId(), sessionId, subChannelId, verifyId);
                ret.put("cuid", channelUserId);
            }
        } else {
            // 第三方登录&验证
            channelUserId = verify(sc.getGameId(), sc.getChannelId(), sessionId, subChannelId, verifyId);
            ret.put("cuid", channelUserId);
        }
        // 日志
        logger.info(deviceUniqueID, sc.getPlayerId(), "", sc.getChannelId(), sc.getGameId(), "reqlogin_server");
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

        if (channelId==null){
            return null;
        }

        // 如果未找到渠道，默认为官方渠道
        IVerifySession verifySession;
        try{
            verifySession = (IVerifySession) context.getBean(channelId);
        }catch (Exception e){
            verifySession = (IVerifySession) context.getBean("1000");
        }

        // 验证渠道是否存在
        if (channelAndVersion == null){
            logger.error("渠道验证错误。。。，请检查渠道["+channelId+"]配置");
            throw new ChannelIDErrorException();
        }
        Map<String, Object> extraParams = new HashMap<String, Object>();
        extraParams.put("verifyId", verifyId);
        extraParams.put("subChannelId", subChannelId);
        extraParams.put("gameId", gameId);

        String channelUserId = verifySession.verify(channelId, sessionId, extraParams);
        return channelUserId;
    }

    public static GameChannelSetting getGameChannelSetting(String gameId, String channelId){
        return C_Game_Channel_Setting.get(gameId + "_" + channelId);
    }
}
