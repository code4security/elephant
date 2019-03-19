package com.sjhy.platform.biz.deploy.utils;

import com.sjhy.platform.biz.deploy.redis.RedisUtil;
import com.sjhy.platform.client.dto.game.ChannelAndVersion;
import com.sjhy.platform.client.dto.game.Game;
import com.sjhy.platform.client.dto.player.PlayerIos;
import com.sjhy.platform.persist.mysql.game.ChannelAndVersionMapper;
import com.sjhy.platform.persist.mysql.game.GameMapper;
import com.sjhy.platform.persist.mysql.player.PlayerIosMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbVerifyUtils {

    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private ChannelAndVersionMapper channelAndVersionMapper;
    @Autowired
    private RedisUtil redis;
    @Autowired
    private PlayerIosMapper playerIosMapper;

    /**
     * 验证gameId是否存在数据库
     * @param gameId
     * @return
     */
    public boolean isHasGame(String gameId){
        try {
            Game game = (Game) redis.get(gameId);
            if (game != null){
                return true;
            }
            game = gameMapper.selectByGameId(gameId);
            if (game != null){
                redis.set(gameId,game);
                redis.expire(gameId,30);
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
            ChannelAndVersion channelAndVersion = (ChannelAndVersion) redis.get(channelId);
            if (channelAndVersion != null){
                return true;
            }
            channelAndVersion = channelAndVersionMapper.verifyChannel
                            (new ChannelAndVersion(null,channelId,gameId,null,null,null,null));
            if (channelAndVersion != null){
                redis.set(channelId,channelAndVersion);
                redis.expire(channelId,30);
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 验证ios玩家id是否存在
     * @param iosId
     * @param gameId
     * @param channelId
     * @return
     */
    public boolean isHasIos(Long iosId, String gameId, String channelId) {
        try {
            PlayerIos playerIos = playerIosMapper.selectByGameId
                    (new PlayerIos(iosId,gameId,channelId,null,null,null,null,null,null));
            if (playerIos != null){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }
}
