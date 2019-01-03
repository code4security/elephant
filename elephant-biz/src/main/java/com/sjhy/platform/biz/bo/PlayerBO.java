package com.sjhy.platform.biz.bo;

import com.sjhy.platform.client.dto.config.ProtocolKey;
import com.sjhy.platform.client.dto.exception.AccountAlreadyBindingOtherException;
import com.sjhy.platform.client.dto.exception.NotExistAccountException;
import com.sjhy.platform.client.dto.utils.IPOperator;
import com.sjhy.platform.client.dto.utils.MD5Util;
import com.sjhy.platform.client.dto.vo.AccountVO;
import com.sjhy.platform.client.dto.game.ModuleSwitch;
import com.sjhy.platform.client.dto.fixed.IpLocation;
import com.sjhy.platform.client.dto.game.ChannelAndVersion;
import com.sjhy.platform.client.dto.player.Player;
import com.sjhy.platform.client.dto.player.PlayerChannel;
import com.sjhy.platform.persist.mysql.fixed.IpLocationMapper;
import com.sjhy.platform.persist.mysql.game.ChannelAndVersionMapper;
import com.sjhy.platform.persist.mysql.game.ModuleSwitchMapper;
import com.sjhy.platform.persist.mysql.player.PlayerChannelMapper;
import com.sjhy.platform.persist.mysql.player.PlayerMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    @Resource
    private IpLocationMapper ipLocationMapper;
    @Resource
    private ModuleSwitchMapper moduleSwitchMapper;

    /**
     * 获取渠道验证
     * @param channelId
     * @param channelUserID
     * @param deviceUniqueID
     * @param gameId
     * @return
     * @throws AccountAlreadyBindingOtherException
     * @throws NotExistAccountException
     */
    public AccountVO getAccount(String channelId, String channelUserID, String deviceUniqueID, String gameId)
            throws AccountAlreadyBindingOtherException, NotExistAccountException
    {
        AccountVO account = new AccountVO();

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

                account.setPlayerId(playerChannel.getPlayerId());

                account.setDeviceUniquelyId(deviceUniqueID);

                account.setPassword(MD5Util.me().md5Hex(deviceUniqueID + ProtocolKey.UNIQUE_DEVICE_ID_KEY
                        + deviceUniqueID.substring(1, 4)));

                account.setServerId(player.getServerId());
            } else {
                //如果玩家不存在的话根据硬件标示设置密码
                account.setPlayerId(0l);
                account.setPassword(MD5Util.me().md5Hex(deviceUniqueID + ProtocolKey.UNIQUE_DEVICE_ID_KEY
                        + deviceUniqueID.substring(1, 4)));
            }

            account.setChUserId(channelUserID);
            account.setChannelId(channelId);
        }
        return account;
    }

    /**
     * 查询玩家总数
     * @return
     */
    public int getTotalPlayerNum() {
        Player player = new Player();
        return playerMapper.selectByCount(player);
    }

    /**
     * 根据玩家唯一标示创建玩家id
     * @param channelId
     * @param deviceUniquelyId
     * @param serverId
     * @param deviceModel
     * @param ip
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public Player createNewPlayerByCooperate(String channelId, String deviceUniquelyId, Integer serverId, String deviceModel, String ip, String gameId) {
        // 需要更新的信息
        Date now = Calendar.getInstance().getTime();
        Date zero = new Date(0);

        Player player = new Player();

        player.setServerId(serverId); // Server_ID(最后一次登陆的服务器id,如果是第一次的话分配一个推荐的服务器)
        player.setDeviceModel(deviceModel); // 硬件类型信息
        player.setDeviceUniquelyId(deviceUniquelyId); // 设备唯一标识(暂定IMEI+MAC与苹果id)
        player.setChannelId(channelId); // 绑定类型(null则未绑定)
        player.setGameId(gameId); // 绑定游戏id

        player.setFirstLoginTime(now); // 首次登录时间
        player.setFirstLoginIp(ip); // 首次登录IP
        player.setLastLoginTime(now); // 最后一次登录时间
        player.setLastLoginIp(ip); // 最后登录IP

        long ipNum = IPOperator.ipToLong(ip);
        List<IpLocation> locationList = ipLocationMapper.selectByStart(ipNum);
        if (locationList != null) {
            IpLocation ipLocation = (IpLocation)locationList.get(0);
            player.setIpRegion(ipLocation.getLocid());
        }

        playerMapper.insert(player);

        return player;
    }

    /**
     * 创建渠道用户id
     * @param channelId
     * @param channelUserId
     * @param playerId
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public void createPlayer(String channelId, String channelUserId, long playerId,String gameId) {

        PlayerChannel playerChannel = new PlayerChannel();
        playerChannel.setChannelId(channelId);
        playerChannel.setGameId(gameId);
        playerChannel.setChannelUserId(channelUserId);
        playerChannel.setPlayerId(playerId);

        playerChannelMapper.insert(playerChannel);
    }

    /**
     * 验证是否需要激活码
     * @param gameid
     * @return
     */
    public boolean checkModule(String gameid,String moduleName){
        ModuleSwitch switchs = new ModuleSwitch();
        switchs.setGameId(gameid);
        switchs.setModuleName(moduleName);

        ModuleSwitch mod = moduleSwitchMapper.selectByModule(switchs);

        // 1.如果没有激活码开关设定数据，默认不用进行激活码验证
        if(mod == null){
            return false;
        }else{
            return mod.getStatus();
        }
    }
}
