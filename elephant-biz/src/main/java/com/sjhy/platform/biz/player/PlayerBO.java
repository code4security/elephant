package com.sjhy.platform.biz.player;

import com.sjhy.platform.client.constant.ProtocolKey;
import com.sjhy.platform.client.dto.base.AccountVO;
import com.sjhy.platform.client.dto.game.ChannelAndVersion;
import com.sjhy.platform.client.dto.player.Player;
import com.sjhy.platform.client.dto.player.PlayerChannel;
import com.sjhy.platform.client.enumerate.ChannelEnum;
import com.sjhy.platform.client.exception.AccountAlreadyBindingOtherException;
import com.sjhy.platform.client.exception.NotExistAccountException;
import com.sjhy.platform.client.utils.MD5Util;
import com.sjhy.platform.persist.mysql.game.ChannelAndVersionMapper;
import com.sjhy.platform.persist.mysql.player.PlayerChannelMapper;
import com.sjhy.platform.persist.mysql.player.PlayerMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @HJ
 */
@Service
public class PlayerBO {

    private static final Logger logger = Logger.getLogger( LoginBO.class );
    @Resource
    private PlayerChannelMapper playerChannelMapper;
    @Resource
    private ChannelAndVersionMapper channelAndVersionMapper;
    @Resource
    private PlayerMapper playerMapper;

    public AccountVO getAccount(String channelId, String channelUserID, String deviceUniqueID, String cooperateID,String gameId)
            throws AccountAlreadyBindingOtherException, NotExistAccountException
    {
        AccountVO account = new AccountVO();

        logger.debug("cooperateID================>"+cooperateID);

        ChannelAndVersion channelAndVersion = new ChannelAndVersion();
        channelAndVersion.setChannelId(channelId);
        channelAndVersion.setGameId(gameId);

        ChannelAndVersion channelAndVersion1 = channelAndVersionMapper.verifyChannel(channelAndVersion);
        if (channelAndVersion1 != null){
            //根据传入的账号ID查询玩家91绑定关系表
            PlayerChannel playChannel = new PlayerChannel();
            playChannel.setGameId(gameId);
            playChannel.setChannelId(channelId);
            playChannel.setChannelUserId(channelUserID);

            PlayerChannel playerChannel = playerChannelMapper.selectByChannelUserId(playChannel);

            if ( playerChannel != null ) {
                Player play = new Player();
                play.setChannelId(channelId);
                play.setPlayerId(playerChannel.getPlayerId());

                Player player = playerMapper.selectByPlayerId(play);

                account.setId(playerChannel.getPlayerId());

                account.setName(deviceUniqueID);

                account.setPassword(MD5Util.me().md5Hex(deviceUniqueID + ProtocolKey.UNIQUE_DEVICE_ID_KEY
                        + deviceUniqueID.substring(1, 4)));

                account.setServerId(player.getServerId());
            } else {
                //如果玩家不存在的话根据硬件标示设置密码
                account.setId(0l);
                account.setPassword(MD5Util.me().md5Hex(deviceUniqueID + ProtocolKey.UNIQUE_DEVICE_ID_KEY
                        + deviceUniqueID.substring(1, 4)));
            }

            account.setChUserId(channelUserID);
            account.setChannelId(channelId);
        }
        return account;
    }
}
