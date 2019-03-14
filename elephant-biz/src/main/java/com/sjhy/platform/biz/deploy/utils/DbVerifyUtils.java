package com.sjhy.platform.biz.deploy.utils;

import com.sjhy.platform.biz.deploy.redis.RedisUtil;
import com.sjhy.platform.client.dto.game.ChannelAndVersion;
import com.sjhy.platform.client.dto.game.Game;
import com.sjhy.platform.persist.mysql.game.ChannelAndVersionMapper;
import com.sjhy.platform.persist.mysql.game.GameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DbVerifyUtils {

    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private ChannelAndVersionMapper channelAndVersionMapper;
    @Autowired
    private RedisUtil redis;

    /**
     * 验证gameId是否存在数据库
     * @param gameId
     * @return
     */
    public boolean isHasGame(String gameId){
        try {
            Game game = gameMapper.selectByGameId(gameId);
            if (game != null){
                return true;
            }else {
                return false;
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
    public boolean isHasChannel(String channelId,String gameId){
        try {
            ChannelAndVersion channelAndVersion =
                    channelAndVersionMapper.verifyChannel(new ChannelAndVersion(null,channelId,gameId,null,null,null,null));
            if (channelAndVersion != null){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }
}
