package com.sjhy.platform.biz.bo;

import com.sjhy.platform.biz.config.AppConfig;
import com.sjhy.platform.biz.config.KairoErrorCode;
import com.sjhy.platform.biz.enumerate.ModuleEnum;
import com.sjhy.platform.biz.exception.*;
import com.sjhy.platform.biz.redis.RedisService;
import com.sjhy.platform.biz.srp.SRPAuthenticationFailedException;
import com.sjhy.platform.biz.srp.SRPFactory;
import com.sjhy.platform.biz.srp.SRPVerifier;
import com.sjhy.platform.biz.utils.StringUtils;
import com.sjhy.platform.biz.utils.UtilDate;
import com.sjhy.platform.biz.vo.*;
import com.sjhy.platform.client.dto.game.ChannelAndVersion;
import com.sjhy.platform.client.dto.game.Game;
import com.sjhy.platform.client.dto.game.Server;
import com.sjhy.platform.client.dto.history.PlayerLoginLog;
import com.sjhy.platform.client.dto.player.Player;
import com.sjhy.platform.persist.mysql.game.ChannelAndVersionMapper;
import com.sjhy.platform.persist.mysql.game.ServerMapper;
import com.sjhy.platform.persist.mysql.history.PlayerLoginLogMapper;
import com.sjhy.platform.persist.mysql.player.PlayerMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * @HJ
 */
@Service
public class LoginBO {

    private static final Logger logger = Logger.getLogger( LoginBO.class );
    @Resource
    private PlayerBO playerBO;
    @Resource
    private Properties serverConfig;
    @Resource
    private GiftCodeBO giftCodeBO;
    @Resource
    private ChannelAndVersionMapper channelAndVersionMapper;
    @Resource
    private PlayerMapper playerMapper;
    @Resource
    private ServerHistoryBO serverHistoryBO;
    @Resource
    private PlayerLoginLogMapper playerLoginLogMapper;
    @Resource
    private ServerMapper serverMapper;
    @Resource
    private RedisService redisService;

    public static String ServerCloseEnterTime = "";
    public static String ServerTotalPlayers   = "";


    /**
     * 第一次握手
     * @param clientId           客户端id
     * @param channelUserId     渠道账号id
     * @param deviceUniqueId    设备id
     * @param channelId          渠道id
     * @param gameId             游戏id
     * @return
     * @throws DeviceSignNullException
     * @throws EmptyAccountNameException
     * @throws AccountAlreadyBindingOtherException
     * @throws NotExistAccountException
     */
    public RegularLoginVO loginChallenge(int clientId, String channelUserId, String deviceUniqueId, String channelId, String gameId) throws DeviceSignNullException,
            EmptyAccountNameException, AccountAlreadyBindingOtherException, NotExistAccountException
    {
        logger.error("LoginService|======================>loginChallenge");

        // 判断是否是空的账号名或是空的设备唯一标识
        if (StringUtils.isBlank( channelUserId ) ||  StringUtils.isBlank( deviceUniqueId ))
            throw new EmptyAccountNameException();

        logger.error("loginChallenge|deviceUniqueID="+deviceUniqueId);

        // 获取账户信息
        AccountVO account = playerBO.getAccount(channelId, channelUserId, deviceUniqueId, gameId);
        RegularLoginVO result = new RegularLoginVO();

        // 产生校验器
        logger.error("loginChallenge|"+account.getPassword());

        SRPVerifier verifier = SRPFactory.getInstance().makeVerifier( ( account.getPassword() ).getBytes() );

        // 生成加密验证session
        LoginSessionVO sessionVO = new LoginSessionVO();
        sessionVO.Session = SRPFactory.getInstance().newServerSession( verifier );

        //缓存
        //根据返回的account判断是不是该设备第一次登陆游戏，如果不是第一次登陆游戏accountID大于0;如果是第一次登陆游戏的accountID等于0，将设备唯一标识放入到sessionVO的AccountName中
        if ( account.getPlayerId() > 0 ) {
            sessionVO.isFirstLogin = false;
            sessionVO.playerId = account.getPlayerId();
            sessionVO.deviceUniquelyId = account.getDeviceUniquelyId();
            sessionVO.ServerId  = account.getServerId();
            sessionVO.channelUserId = account.getChUserId();
            sessionVO.channelId = channelId;
        } else {
            sessionVO.isFirstLogin = true;
            sessionVO.playerId = 0;
            sessionVO.deviceUniquelyId = deviceUniqueId;
            sessionVO.ServerId = 0;
            sessionVO.channelUserId = channelUserId;
            sessionVO.channelId = channelId;
        }
        // 玩家激活状况
        sessionVO.activationState = account.getActivationState();

        // 缓存(注释)
        /*sessionCacheMgr.put(clientId, sessionVO);
        channelUserMgr.put(channelUserID, gameId, account);*/
        
        // 构造结果
        result.getSrp6Info().setSalt( verifier.salt_s );
        result.getSrp6Info().setB( sessionVO.Session.getPublicKey_B() );

        // 埋点(注释)
        // logBO.setMdEventLog(deviceUniqueID, account.getChUserId(), account.getId().toString(), account.getChannelId(), gameId, "loginsessionone_server");

        // 返回结果
        return result;
    }

    /**
     * 第二次握手
     * @param clientId
     * @param ip
     * @param a
     * @param m1
     * @param sdkVersion
     * @param serverId
     * @param gameId
     * @return
     * @throws NotChallengeYetException
     * @throws SRPAuthenticationFailedException
     * @throws KairoException
     */
    public RegularLoginVO loginProof(int clientId, String ip, BigInteger a, BigInteger m1 , String sdkVersion, int serverId, String gameId)
            throws NotChallengeYetException, SRPAuthenticationFailedException, KairoException
    {
        logger.error("LoginService|======================>loginProof");

        // 获取加密验证session
        LoginSessionVO sessionVO = new LoginSessionVO();

        // 缓存（注释）
        /* sessionVO = sessionCacheMgr.get( clientId );
        sessionCacheMgr.remove( clientId );
        if ( sessionVO == null ){ throw new NotChallengeYetException();}
        Game game = KrGlobalCache.getGameById(gameId);
        if(game == null){ throw new NotChallengeYetException();}*/

        // 使用客户端的公匙A-暂时删除
        sessionVO.Session.setClientPublicKey_A( a );
        // 计算S-暂时删除
        sessionVO.Session.computeCommonValue_S();
        // 针对客户端的M1进行校验-暂时删除
        sessionVO.Session.validateClientEvidenceValue_M1( m1 );

        long playerId = 0;
        String channelId = sessionVO.channelId;

        // 如果合作方是91的话判断91ID是否创建过账号，没有的话直接生成相应的账号;如果是机锋等合作方也判断是否创建过账号，没有的话自动创建
        if (sessionVO.isFirstLogin) {
            if(ServerCloseEnterTime == "") {
                ServerCloseEnterTime = serverConfig.getProperty(AppConfig.SERVER_CLOSE_ENTER_TIME);
            }

            if(ServerTotalPlayers == "") {
                ServerTotalPlayers = serverConfig.getProperty(AppConfig.SERVER_TOTAL_PLAYERS);
            }
            int serverTotalPlayers = StringUtils.getInt(ServerTotalPlayers);

            // 先检查时间是否满足
            if(ServerCloseEnterTime != null && !ServerCloseEnterTime.trim().equals("0")) {
                Date closeTime = UtilDate.text2Date(ServerCloseEnterTime, UtilDate.DATETIME_PATTERN);

                if(closeTime != null && closeTime.getTime() <= Calendar.getInstance().getTimeInMillis()){
                    throw new KairoException(KairoErrorCode.ERROR_SERVER_IS_FULL);
                }
            }
            if(serverTotalPlayers > 0 && playerBO.getTotalPlayerNum() > serverTotalPlayers) {
                throw new KairoException(KairoErrorCode.ERROR_SERVER_IS_FULL);
            }

            // 使用sessionVO.AccountName中保存的设备唯一标识建立账号
            Player player = playerBO.createNewPlayerByCooperate(channelId, sessionVO.deviceUniquelyId, serverId, "", ip, gameId);
            playerId = player.getPlayerId();

            playerBO.createPlayer(sessionVO.channelId, sessionVO.channelUserId, player.getPlayerId(),gameId);

            //缓存(注释)
            // channelUserMgr.update(sessionVO.CooperateId, playerId, gameId);
        } else {
            playerId = sessionVO.playerId;
            serverId = sessionVO.ServerId;
        }

        // 缓存（注释）
        // 保存到redis 存储的格式为键:playerId, 值:SessionKey
        // SessionKeyHMapCpt.saveOrUpdate(playerId+"_"+gameId, sessionVO.Session.getSessionCommonValue().toString(16));

        // 构造结果
        RegularLoginVO result = new RegularLoginVO();

        result.setPlayerId( playerId );

        // 告知M2
        result.getSrp6Info().setM2(sessionVO.Session.getEvidenceValue_M2());

        boolean isOpenActivation = playerBO.checkModule(gameId,ModuleEnum.ACTIVATIONCODE.getModuleName());

        // 是否需要激活码(激活码开关开启  && 玩家未激活状态=>1：已激活、0：未激活)
        if(isOpenActivation && !giftCodeBO.isMeActivated(gameId, playerId)){
            result.setNeedActivation(true);
        }else{
            result.setNeedActivation(false);
        }

        // 埋点(注释)
        /*AccountVO account = channelUserMgr.get(sessionVO.CooperateId +"_"+ gameId);
        if(account == null) {
            logBO.setMdEventLog("", sessionVO.CooperateId, playerId+"", "", gameId, "loginsessiontwo_server");
        }else{
            logBO.setMdEventLog(account.getDeviceUniquelyCode(), account.getChUserId(), playerId+"", account.getChannelId(), gameId, "loginsessiontwo_server");
        }*/

        return result;
    }

    /**
     * 验证是否需要进行游戏版本更新
     * @param gameId
     * @param channelId
     * @param versionNum
     * @return
     */
    public ChannelAndVersionVO checkChannelAndVersion(String gameId, String channelId, String versionNum) {
        ChannelAndVersion cav = new ChannelAndVersion();
        cav.setGameId(gameId);
        cav.setChannelId(channelId);
        ChannelAndVersion channelAndVersion = channelAndVersionMapper.verifyChannel(cav);

        logger.error("LoginService|======================>checkChannelAndVersion");

        // 不存在的渠道号,直接认为通过了,不管理该渠道
        if(channelAndVersion == null){
            return new ChannelAndVersionVO(true,false,"");
        }

        // 版本号相等则不需要升级,否则需要升级
        if(channelAndVersion.getVersionNum().trim().equals(versionNum.trim())/* || true */){
            return new ChannelAndVersionVO();
        }

        return new ChannelAndVersionVO(true,true, channelAndVersion.getVersionDownload()); // 暂时都是强制升级
    }

    /**
     * 确认登录服务器
     * @param playerId
     * @param gameId
     * @param serverId
     * @param ip
     * @param channelId
     * @param activationCode
     * @return
     * @throws FreezeTheAccountException
     * @throws NotChallengeYetException
     * @throws NotExistAccountException
     * @throws IpIsNotInWhiteListException
     * @throws ActivationCodeIsNotRightException
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public LoginVO confirmServer(long playerId, String gameId, int serverId, String ip, String channelId, String activationCode)  throws FreezeTheAccountException, NotChallengeYetException, NotExistAccountException, IpIsNotInWhiteListException, ActivationCodeIsNotRightException
    {
        logger.error("LoginService|======================>confirmServer");
        // 缓存（注释）
        // 从redis中根据playerId获取sessionkey，如果sessionkey不存在的话报为经过srp6验证的过程
        /*String sessionKey = SessionKeyHMapCpt.getSessionKey(playerId + "_" + gameId);
        if ( (sessionKey == null) || ("".equals(sessionKey)) ) {
            throw new NotChallengeYetException();
        }*/

        // 查找player表看用户是否存在
        Player players = new Player();
        players.setPlayerId(playerId);
        players.setGameId(gameId);
        Player player = playerMapper.selectByPlayerId(players);
        if ( player == null ) {
            throw new NotExistAccountException();
        }

        // 激活码激活逻辑
        boolean isActivationOpen = playerBO.checkModule(gameId,ModuleEnum.ACTIVATIONCODE.getModuleName());

        // 需要激活
        if(isActivationOpen && !giftCodeBO.isMeActivated(gameId, playerId)){
            // 激活处理
            int ret = giftCodeBO.activateCode(activationCode, gameId, playerId, channelId);
            if(ret < 0){
                logger.error("channelId:" + channelId);
                throw new ActivationCodeIsNotRightException();
            }
        }

        Date now  = Calendar.getInstance().getTime();

        // 完成具体的登录相关过程
        player.setServerId(serverId); // Server_ID(最后一次登陆的服务器id)
        player.setLastLoginTime(now); // 最后一次登录时间
        player.setLastLoginIp(ip); // 最后登录IP
        playerMapper.updateByPrimaryKey(player);

        // 记录登录服务器的历史记录
        serverHistoryBO.updateServerHistory(playerId, serverId, gameId ,channelId); // 注意,这里 入参 serverId 与 player.getServerId() 不一定一样

        // 记录登录日志
        PlayerLoginLog record = new PlayerLoginLog();
        // record.setDeviceModel(player.getDeviceModel()); // 硬件型号信息
        record.setCreateTime(now); // 创建时间
        record.setPlayerId(playerId); // 玩家id
        record.setIsLogin(true); // 是否是登陆(否则表示退出)
        record.setServerId(serverId); // 登陆的ServiceId
        record.setGameId(gameId); // 游戏id
        record.setDeviceUniquelyId(player.getDeviceUniquelyId()); // 设备唯一标示
        if(channelId != null){
            record.setChannelId(channelId);  // 游戏分发渠道
        }
        playerLoginLogMapper.insert(record);

        // 获取服务器版本号
        Server servers = new Server();
        servers.setGameId(gameId);
        servers.setServerId(serverId);
        Server server = serverMapper.selectByServer(servers);

        // 埋点（注释）
        //logService.saveLoginLog(channelId, channelUserMgr.getChUserId(playerId, gameId), playerId+"", gameId, ip);

        return new LoginVO(player.getPlayerId(),player.getServerId(),server.getVersionNum());
    }
}
