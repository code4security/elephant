package com.sjhy.platform.biz.bo;

import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.biz.deploy.config.AppConfig;
import com.sjhy.platform.biz.deploy.config.KairoErrorCode;
import com.sjhy.platform.client.dto.enumerate.ModuleEnum;
import com.sjhy.platform.biz.deploy.exception.*;
import com.sjhy.platform.client.dto.srp.SRPAuthenticationFailedException;
import com.sjhy.platform.client.dto.srp.SRPFactory;
import com.sjhy.platform.client.dto.srp.SRPVerifier;
import com.sjhy.platform.biz.deploy.utils.StringUtils;
import com.sjhy.platform.biz.deploy.utils.UtilDate;
import com.sjhy.platform.client.dto.vo.*;
import com.sjhy.platform.client.dto.game.ChannelAndVersion;
import com.sjhy.platform.client.dto.game.Game;
import com.sjhy.platform.client.dto.game.Server;
import com.sjhy.platform.client.dto.history.PlayerLoginLog;
import com.sjhy.platform.client.dto.player.Player;
import com.sjhy.platform.client.dto.player.PlayerBanList;
import com.sjhy.platform.client.dto.vo.AccountVO;
import com.sjhy.platform.client.dto.vo.LoginSessionVO;
import com.sjhy.platform.client.dto.vo.RegularLoginVO;
import com.sjhy.platform.persist.mysql.game.ChannelAndVersionMapper;
import com.sjhy.platform.persist.mysql.game.GameMapper;
import com.sjhy.platform.persist.mysql.game.ServerMapper;
import com.sjhy.platform.persist.mysql.history.PlayerLoginLogMapper;
import com.sjhy.platform.persist.mysql.player.PlayerBanListMapper;
import com.sjhy.platform.persist.mysql.player.PlayerMapper;
import com.sjhy.platform.persist.mysql.player.PlayerRoleMapper;
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

    private ChannelAndVersionMapper channelAndVersionMapper;

    private PlayerMapper playerMapper;
    @Resource
    private ServerHistoryBO serverHistoryBO;

    private PlayerLoginLogMapper playerLoginLogMapper;

    private ServerMapper serverMapper;

    private GameMapper gameMapper;

    private PlayerRoleMapper playerRoleMapper;
    @Resource
    private RoleBO roleBO;

    private PlayerBanListMapper playerBanListMapper;
    @Resource
    private GameBO gameBO;

    public static String ServerCloseEnterTime = "";
    public static String ServerTotalPlayers   = "";


    /**
     * 第一次握手
     * @param clientId           客户端id
     * @param deviceUniqueId    设备id
     * @return
     * @throws DeviceSignNullException
     * @throws EmptyAccountNameException
     * @throws AccountAlreadyBindingOtherException
     * @throws NotExistAccountException
     */
    public RegularLoginVO loginChallenge(ServiceContext sc, int clientId, String deviceUniqueId) throws DeviceSignNullException,
            EmptyAccountNameException, AccountAlreadyBindingOtherException, NotExistAccountException
    {
        logger.error("LoginService|======================>loginChallenge");

        // 判断是否是空的账号名或是空的设备唯一标识
        if (StringUtils.isBlank( sc.getChannelUserId() ) ||  StringUtils.isBlank( deviceUniqueId ))
            throw new EmptyAccountNameException();

        logger.error("loginChallenge|deviceUniqueID="+deviceUniqueId);

        // 获取账户信息
        AccountVO account = playerBO.getAccount(sc, deviceUniqueId);
        RegularLoginVO result = new RegularLoginVO();

        // 产生校验器
        logger.error("loginChallenge|"+account.getPassword());

        SRPVerifier verifier = SRPFactory.getInstance().makeVerifier( ( account.getPassword() ).getBytes() );

        // 生成加密验证session
        LoginSessionVO sessionVO = new LoginSessionVO();
        sessionVO.Session = SRPFactory.getInstance().newServerSession( verifier );

        //根据返回的account判断是不是该设备第一次登陆游戏，如果不是第一次登陆游戏accountID大于0;如果是第一次登陆游戏的accountID等于0，将设备唯一标识放入到sessionVO的AccountName中
        if ( account.getPlayerId() > 0 ) {
            sessionVO.isFirstLogin = false;
            sessionVO.playerId = account.getPlayerId();
            sessionVO.deviceUniquelyId = account.getDeviceUniquelyId();
            sessionVO.ServerId  = account.getServerId();
            sessionVO.channelUserId = account.getChUserId();
            sessionVO.channelId = sc.getChannelId();
        } else {
            sessionVO.isFirstLogin = true;
            sessionVO.playerId = 0;
            sessionVO.deviceUniquelyId = deviceUniqueId;
            sessionVO.ServerId = 0;
            sessionVO.channelUserId = sc.getChannelUserId();
            sessionVO.channelId = sc.getChannelId();
        }
        // 玩家激活状况
        sessionVO.activationState = account.getActivationState();

        // 缓存（注释）
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
     * @return
     * @throws NotChallengeYetException
     * @throws SRPAuthenticationFailedException
     * @throws KairoException
     */
    public RegularLoginVO loginProof(ServiceContext sc, int clientId, String ip, BigInteger a, BigInteger m1 , String sdkVersion)
            throws NotChallengeYetException, SRPAuthenticationFailedException, KairoException
    {
        logger.error("LoginService|======================>loginProof");

        // 获取加密验证session
        LoginSessionVO sessionVO = new LoginSessionVO();

        // 缓存（注释）
        /* sessionVO = sessionCacheMgr.get( clientId );
        sessionCacheMgr.remove( clientId );
        if ( sessionVO == null ){ throw new NotChallengeYetException();}*/
        Game game = gameMapper.selectByGameId(sc.getGameId());
        if (game == null){
            throw new NotChallengeYetException();
        }

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
            Player player = playerBO.createNewPlayerByCooperate(sc, sessionVO.deviceUniquelyId, "", ip);
            playerId = player.getPlayerId();

            playerBO.createPlayer(sc);

            //缓存（注释）
            // channelUserMgr.update(sessionVO.CooperateId, playerId, gameId);
        } else {
            playerId = sessionVO.playerId;
            // serverId = sessionVO.ServerId;
        }

        // 缓存（注释）
        // 保存到redis 存储的格式为键:playerId, 值:SessionKey
        // SessionKeyHMapCpt.saveOrUpdate(playerId+"_"+gameId, sessionVO.Session.getSessionCommonValue().toString(16));

        // 构造结果
        RegularLoginVO result = new RegularLoginVO();

        result.setPlayerId( playerId );

        // 告知M2
        result.getSrp6Info().setM2(sessionVO.Session.getEvidenceValue_M2());

        boolean isOpenActivation = playerBO.checkModule(sc,ModuleEnum.ACTIVATIONCODE.getModuleName());

        // 是否需要激活码(激活码开关开启  && 玩家未激活状态=>1：已激活、0：未激活)
        if(isOpenActivation && !giftCodeBO.isMeActivated(sc)){
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
     * @param versionNum
     * @return
     */
    public ChannelAndVersionVO checkChannelAndVersion(ServiceContext sc, String versionNum) {
        ChannelAndVersion cav = new ChannelAndVersion();
        cav.setGameId(sc.getGameId());
        cav.setChannelId(sc.getChannelId());
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
     * @param ip
     * @param activationCode
     * @return
     * @throws FreezeTheAccountException
     * @throws NotChallengeYetException
     * @throws NotExistAccountException
     * @throws IpIsNotInWhiteListException
     * @throws ActivationCodeIsNotRightException
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public LoginVO confirmServer(ServiceContext sc, String ip, String activationCode)  throws FreezeTheAccountException, NotChallengeYetException, NotExistAccountException, IpIsNotInWhiteListException, ActivationCodeIsNotRightException
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
        players.setPlayerId(sc.getPlayerId());
        players.setGameId(sc.getGameId());
        Player player = playerMapper.selectByPlayerId(players);
        if ( player == null ) {
            throw new NotExistAccountException();
        }

        // 激活码激活逻辑
        boolean isActivationOpen = playerBO.checkModule(sc,ModuleEnum.ACTIVATIONCODE.getModuleName());

        // 需要激活
        if(isActivationOpen && !giftCodeBO.isMeActivated(sc)){
            // 激活处理
            int ret = giftCodeBO.activateCode(sc, activationCode);
            if(ret < 0){
                logger.error("channelId:" + sc.getChannelId());
                throw new ActivationCodeIsNotRightException();
            }
        }

        Date now  = Calendar.getInstance().getTime();

        // 完成具体的登录相关过程
        player.setServerId(sc.getServerId()); // Server_ID(最后一次登陆的服务器id)
        player.setLastLoginTime(now); // 最后一次登录时间
        player.setLastLoginIp(ip); // 最后登录IP
        playerMapper.updateByPrimaryKey(player);

        // 记录登录服务器的历史记录
        serverHistoryBO.updateServerHistory(sc); // 注意,这里 入参 serverId 与 player.getServerId() 不一定一样

        // 记录登录日志
        PlayerLoginLog record = new PlayerLoginLog();
        // record.setDeviceModel(player.getDeviceModel()); // 硬件型号信息
        record.setCreateTime(now); // 创建时间
        record.setPlayerId(sc.getPlayerId()); // 玩家id
        record.setIsLogin(true); // 是否是登陆(否则表示退出)
        record.setServerId(sc.getServerId()); // 登陆的ServiceId
        record.setGameId(sc.getGameId()); // 游戏id
        record.setDeviceUniquelyId(player.getDeviceUniquelyId()); // 设备唯一标示
        if(sc.getChannelId() != null){
            record.setChannelId(sc.getChannelId());  // 游戏分发渠道
        }
        playerLoginLogMapper.insert(record);

        // 获取服务器版本号
        Server servers = new Server();
        servers.setGameId(sc.getGameId());
        servers.setServerId(sc.getServerId());
        Server server = serverMapper.selectByServer(servers);

        // 埋点（注释）
        //logService.saveLoginLog(channelId, channelUserMgr.getChUserId(playerId, gameId), playerId+"", gameId, ip);

        return new LoginVO(player.getPlayerId(),player.getServerId(),server.getVersionNum());
    }

    /**
     * 验证角色登陆，返回角色基本信息
     * @param deviceModel
     * @param deviceToken
     * @return
     * @throws PleaseLoginAgainException
     * @throws NoSuchRoleException
     * @throws AlreadyExistsPlayerRoleException
     * @throws AdmiralNameIsNotNullableException
     * @throws AdmiralNameIsTooLongException
     * @throws AdmiralNameIncludeHarmonyException
     * @throws AdmiralNameCoincideException
     * @throws CreateRoleException
     * @throws GameIdIsNotExsitsException
     * @throws KairoException
     */
    public PlayerRoleVO enterGame(ServiceContext sc, int deviceModel, String deviceToken) throws PleaseLoginAgainException, NoSuchRoleException, AlreadyExistsPlayerRoleException, AdmiralNameIsNotNullableException, AdmiralNameIsTooLongException, AdmiralNameIncludeHarmonyException, AdmiralNameCoincideException, CreateRoleException, GameIdIsNotExsitsException, KairoException {

        // 缓存（注释）
        // 1.验证用户的 sessionKey
        /* String redisSessionKey = SessionKeyHMapCpt.getSessionKey(userId + "_" + gameId);
        if(redisSessionKey == null){throw new PleaseLoginAgainException();}
        gameServerChannelHandler.setPacketCrypt( new PacketCrypt(redisSessionKey) );*/

        // 2.判断是否已经成功初始化用户信息
        PlayerRoleVO playerRoleVo = (PlayerRoleVO) playerRoleMapper.selectByPlayerId(sc.getGameId(),sc.getPlayerId());
        if(playerRoleVo == null){
            // 首次登陆，自动创建角色
            playerRoleVo = roleBO.createNewPlayer(sc, sc.getPlayerId()+"_"+sc.getGameId(), deviceToken);
        }

        // 判断玩家是否处于封停状态
        PlayerBanList playerBanList = new PlayerBanList();
        playerBanList.setGameId(playerRoleVo.getGameId());
        playerBanList.setChannelId(playerRoleVo.getChannelId());
        playerBanList.setRoleId(playerRoleVo.getRoleId());
        playerBanList = playerBanListMapper.selectByBan(playerBanList);
        if(gameBO.isRealBan(playerBanList)){
            throw new KairoException(KairoErrorCode.ERROR_IS_BANED);
        }
        // 3.更新登录时间
        roleBO.updateLastLoginTime(sc);
        // 缓存（注释）
        // playerRoleService.removeInCache(playerRoleVO.getRoleId());
        // 5.埋点（注释）
        // logService.setRoleLoginLog(playerRoleVO);
        // 6.发送封测奖励
        return playerRoleVo;
    }
}
