package com.sjhy.platform.biz.deploy.utils;

import com.sjhy.platform.biz.deploy.redis.RedisService;
import com.sjhy.platform.client.dto.game.ChannelAndVersion;
import com.sjhy.platform.client.dto.game.Game;
import com.sjhy.platform.persist.mysql.game.ChannelAndVersionMapper;
import com.sjhy.platform.persist.mysql.game.GameMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class DbVerifyUtils {

    @Autowired
    private static GameMapper gameMapper;
    @Autowired
    private static ChannelAndVersionMapper channelAndVersionMapper;
    @Autowired
    private static RedisService redis;

    /**
     * 验证gameId是否存在数据库
     * @param gameId
     * @return
     */
    public static final boolean isGameId(String gameId){
        try {
            Game game = gameMapper.selectByGameId(gameId);
            if (game == null){
                return false;
            }else {
                return true;
            }
        }catch (Exception e){
            return false;
        }

    }

    /**
     * 验证渠道id是否存在
     * @param channelId
     * @param gameId
     * @return
     */
    public static final boolean isChannelId(String channelId,String gameId){
        try {
            ChannelAndVersion channelAndVersion =
                    channelAndVersionMapper.verifyChannel(new ChannelAndVersion(null,channelId,gameId,null,null,null,null));
            if (channelAndVersion == null){
                return false;
            }else {
                return true;
            }
        }catch (Exception e){
            return false;
        }
    }
}
