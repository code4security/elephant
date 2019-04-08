package com.sjhy.platform.biz.bo;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.sjhy.platform.client.deploy.config.AppConfig;
import com.sjhy.platform.client.deploy.config.KairoErrorCode;
import com.sjhy.platform.client.deploy.exception.*;
import com.sjhy.platform.biz.utils.DbVerifyUtils;
import com.sjhy.platform.biz.utils.MD5Util;
import com.sjhy.platform.biz.utils.StringUtils;
import com.sjhy.platform.biz.utils.UtilDate;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.dto.player.PlayerRole;
import com.sjhy.platform.client.dto.vo.AliOssAccessKeyVO;
import com.sjhy.platform.client.dto.vo.AliOssBucketVO;
import com.sjhy.platform.client.dto.game.Game;
import com.sjhy.platform.client.dto.player.PlayerGameOss;
import com.sjhy.platform.client.dto.vo.ReturnVo;
import com.sjhy.platform.persist.mysql.game.GameMapper;
import com.sjhy.platform.persist.mysql.player.PlayerGameOssMapper;
import com.sjhy.platform.persist.mysql.player.PlayerRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @HJ
 */
@Service
public class OssBO {
    private static final Logger logger = LoggerFactory.getLogger(OssBO.class);

    // 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
    private static final String REGION_CN_HANGZHOU = "cn-hangzhou";
    // 当前 STS API 版本
    private static final String STS_API_VERSION = "2015-04-01";
    private static final String BUCKET_KEY = "kairo-game-data";
    private static final String BUCKET_KEY_BACK = "kairo-game-back";
    // key存活时间、单位秒
    private static final int DURATION_SECONDS = 20;
    private static String BaseGameFilePath = null;
    // 上传状态值定义
    private static final int UPLOADING = 1;
    private static final int SUCCESSED = 2;
    private static final int ERROR     = -1;
    private static final int DELETED   = -2;
    // 只有 RAM用户（子账号）才能调用 AssumeRole 接口
    // 阿里云主账号的AccessKeys不能用于发起AssumeRole请求
    // 请首先在RAM控制台创建一个RAM用户，并为这个用户创建AccessKeys
    private static final String RAM_ACCESS_KEY = "LTAIwWUwQr4yVEr1";
    private static final String RAM_ACCESS_KEY_SECRET = "1YIVlGkLyMY1B0JoIfzvCAgrFLyDeK";
    // 此为OSS最大权限账户,用于查询obj信息
    private static final String ACCESS_KEY = "LTAIUvkgCcZ9AofR";
    private static final String ACCESS_KEY_SECRET = "MfyFW2rMk3OnigcWy3HTJrhdCXFcSY";
    // 玩家accesskey保存{roleId{'get/put', AliOssAccessKeyVO}}
    private static Map<Long, ConcurrentHashMap<String, AliOssAccessKeyVO>> C_Player_Accesskey_Map = new ConcurrentHashMap<Long, ConcurrentHashMap<String, AliOssAccessKeyVO>>();
    // 以杭州为例
    private static final String ENDPOINT = "http://oss-cn-hangzhou.aliyuncs.com";
    @Resource
    private PlayerGameOssMapper playerGameOssMapper;
    @Resource
    private Properties serverConfig;
    @Resource
    private GameMapper gameMapper;
    @Resource
    private DbVerifyUtils dbVerifyUtils;
    @Resource
    private ReturnVo returnVo;

    /**
     * 删除oss文件
     * @param operateId
     */
    public void delBucketObjkey(ServiceContext sc, int operateId){
        // 删除文件信息
        PlayerGameOss playerGameOss = playerGameOssMapper.selectByGameKey(operateId,sc.getGameId());
        if(playerGameOss == null){
            return;
        }
        // 异常情况处理
        if(playerGameOss.getRoleId() != sc.getRoleId()){
            return;
        }
        // 更新状态
        playerGameOss.setStatus(DELETED);
        playerGameOss.setUpdateTime(new Date());
        playerGameOss.setEndTime(null);
        playerGameOss.setMetaMd5(null);

        playerGameOssMapper.updateByPrimaryKeySelective(playerGameOss);
    }

    /**
     * 获取oss文件Bucketkeys
     * @param keyType
     * @return
     * @throws OssBucketkeyException
     * @throws NoSuchRoleException
     */
    public List<AliOssAccessKeyVO> getBucketkeys(ServiceContext sc, int keyType) throws OssBucketkeyException, NoSuchRoleException {
        List<AliOssAccessKeyVO> accessKeys = new ArrayList<AliOssAccessKeyVO>();
        // role
        PlayerRole role = dbVerifyUtils.isHasRole(sc.getGameId(),sc.getPlayerId(),sc.getRoleId());
        if(role == null){
            throw new NoSuchRoleException("指定的玩家不存在,roleId="+sc.getRoleId());
        }
        if(1 == keyType){
            AliOssAccessKeyVO accessKey = getOssStsToken(sc.getRoleId(), role.getGameId(), "read", role.getChannelId());
            if(accessKey == null){
                throw new OssBucketkeyException("read key error");
            }
            accessKeys.add(accessKey);
        }else if(2 == keyType){
            AliOssAccessKeyVO accessKey = getOssStsToken(sc.getRoleId(), role.getGameId(), "write", role.getChannelId());
            if(accessKey == null){
                throw new OssBucketkeyException("write key error");
            }
            accessKeys.add(accessKey);
        }else if(-1 == keyType){
            AliOssAccessKeyVO accessReadKey = getOssStsToken(sc.getRoleId(), role.getGameId(), "read", role.getChannelId());

            if(accessReadKey == null){
                throw new OssBucketkeyException("read key error");
            }
            accessKeys.add(accessReadKey);
            AliOssAccessKeyVO accessWriteKey = getOssStsToken(sc.getRoleId(), role.getGameId(), "write", role.getChannelId());
            // still null
            if(accessWriteKey == null){
                throw new OssBucketkeyException("write key error");
            }
            accessKeys.add(accessWriteKey);
        }
        return accessKeys;
    }

    /**
     * 取得用户上传信息
     * @return
     * @throws NoSuchRoleException
     */
    public List<AliOssBucketVO> getBucket(ServiceContext sc) throws NoSuchRoleException{
        // role
        PlayerRole role = dbVerifyUtils.isHasRole(sc.getGameId(),sc.getPlayerId(),sc.getRoleId());
        if(role == null){
            throw new NoSuchRoleException("指定的玩家不存在,roleId="+sc.getRoleId());
        }
        List<PlayerGameOss> playerGameOss = null;
        List<AliOssBucketVO> roleBuckets  = new ArrayList<AliOssBucketVO>();

        // 相关文件未进行迁移（注释）（1）
        /*GameServerChannelHandler cch = GameServerSendService.getInstance().GetPlayerOnlineById(roleId);
        if(cch == null) {
            playerGameOss = playerGameOssMapper.selectByRoleId(gameId,roleId);
        }else{
            playerGameOss = new ArrayList<PlayerGameOss>(cch.getPlayerGameOss().values());
        }*/
        playerGameOss = dbVerifyUtils.isHasOss(sc.getGameId(),sc.getRoleId());

        if(playerGameOss != null && playerGameOss.size() > 0){
            for(PlayerGameOss val : playerGameOss){
                AliOssBucketVO bucket = new AliOssBucketVO();
                bucket.setBucket(val.getBucket());
                bucket.setGameId(role.getGameId());
                bucket.setObjkey(val.getObjKey());
                bucket.setOperateId(val.getId());
                bucket.setGameId(sc.getGameId());
                bucket.setGold(val.getGold()==null?0:val.getGold());
                bucket.setSaveTime(val.getSaveTime()==null?"":val.getSaveTime());
                bucket.setMetaMd5(val.getMetaMd5()==null?"":val.getMetaMd5());
                bucket.setFname(val.getFname());
                bucket.setType(val.getObjType());
                bucket.setUpdateTime(val.getEndTime());
                roleBuckets.add(bucket);
            }
        }
        return roleBuckets;
    }

    /**
     * 更新上传进度(客户端通知服务器上传结束)
     * @param operateId
     * @throws KairoException
     * @throws NoSuchRoleException
     */
    public void notifyEndPut(ServiceContext sc, int operateId, int result, String md5) throws KairoException, NoSuchRoleException{
        Date now = new Date();

        // 删除文件信息
        PlayerGameOss playerGameOss  = null;

        /*GameServerChannelHandler cch = GameServerSendService.getInstance().GetPlayerOnlineById(roleId);*/
        if(operateId > 0) {
            playerGameOss = dbVerifyUtils.isHasOssGameKey(operateId,sc.getGameId());
            // （注释）（1）
            /*if(cch == null) {
                playerGameOss = playerGameOssMapper.selectByPrimaryKey(operateId);
            }else{
                playerGameOss = cch.getPlayerGameOssByOperateId(operateId);
            }*/
        }else{
            // 备份存档(不校验也备份)
            setPlayerRoleOssFile(sc.getRoleId(),sc.getGameId());
        }
        // role
        PlayerRole role = dbVerifyUtils.isHasRole(sc.getGameId(),sc.getPlayerId(),sc.getRoleId());
        if(role == null){
            throw new NoSuchRoleException("指定的玩家不存在,roleId="+sc.getRoleId());
        }
        if(playerGameOss == null) {
            // 如果result是成功状态则更新
            if(result == 1) {
                playerGameOssMapper.updateEndtimeByRoleId(sc.getGameId(),sc.getRoleId());
                // 刷新缓存 （注释）（2）
                /*if(cch != null) {
                    cch.refreshPlayerGameOssTime(now);
                    // 也需要刷新一下
                    sendUpdatePlayerBucketInfo(role, cch.getPlayerGameOss());
                }*/
            }
            return;
        }
        if(StringUtils.isBlank(md5)){
            throw new KairoException(KairoErrorCode.ERROR_PARAM, "【md5】参数错误");
        }
        if("1234567890abcdefg0WindowsTest".equals(md5)){
            playerGameOss.setStatus(SUCCESSED);
            playerGameOss.setMetaMd5(md5);

            // 参数返回（注释）（2）
            // sendUpdatePlayerBucketInfo(role, playerGameOss);
        }else{
            OSSClient client = null;
            try {
                if(C_Player_Accesskey_Map.get(sc.getRoleId()) == null || C_Player_Accesskey_Map.get(sc.getRoleId()).get("read") == null){
                    // 重新获得key
                    AliOssAccessKeyVO roleAccessKey = getOssStsToken(sc.getRoleId(), role.getGameId(), "read", role.getChannelId());
                    client = new OSSClient(getProperty(AppConfig.ALI_OSS_ENDPOINT_INTERNAL, ENDPOINT), roleAccessKey.getKeyId(), roleAccessKey.getKeySecret(), roleAccessKey.getToken());
                }else{
                    // 用户删除access token取得
                    AliOssAccessKeyVO roleAccessKey = C_Player_Accesskey_Map.get(sc.getRoleId()).get("read");
                    client = new OSSClient(getProperty(AppConfig.ALI_OSS_ENDPOINT_INTERNAL, ENDPOINT), roleAccessKey.getKeyId(), roleAccessKey.getKeySecret(), roleAccessKey.getToken());
                }
                // 获取文件的元信息
                ObjectMetadata metadata = client.getObjectMetadata(playerGameOss.getBucket(), playerGameOss.getObjKey());
                if(metadata != null && md5.equals(metadata.getContentMD5())){
                    playerGameOss.setStatus(SUCCESSED);
                    playerGameOss.setMetaMd5(md5);
                    // 参数返回（注释）（2）
                    // sendUpdatePlayerBucketInfo(role, playerGameOss);
                }else{
                    logger.error("AliyunOssStsService|method=notifyEndPut|存档md5不一致");
                }
            } catch (OSSException oe) {
                System.out.println("Caught an OSSException, which means your request made it to OSS, "
                        + "but was rejected with an error response for some reason.");
                System.out.println("Error Message: " + oe.getErrorCode() + "|method=notifyEndPut");
                System.out.println("Error Code:       " + oe.getErrorCode());
                System.out.println("Request ID:      " + oe.getRequestId());
                System.out.println("Host ID:           " + oe.getHostId());
                playerGameOss.setStatus(ERROR);
            } finally {
                if(client != null){
                    client.shutdown();
                }
            }
        }
        playerGameOss.setEndTime(now);
        playerGameOss.setUpdateTime(now);
        playerGameOssMapper.updateByPrimaryKeySelective(playerGameOss);
        // 刷新缓存（注释）（2）
        /*if(cch != null) {
            cch.setPlayerGameOssByType(playerGameOss.getObjType(), playerGameOss);
        }*/
    }

    /**
     * 申请上传操作
     * @param objKey
     * @throws NoSuchRoleException
     * @throws AdmiralNameIsTooLongException
     * @throws IsHaveSpecialCharacterException
     * @throws KairoException
     */
    public ReturnVo putBucket(ServiceContext sc, String objKey, long gold, String saveTime, String fname, int objType) throws NoSuchRoleException, AdmiralNameIsTooLongException, IsHaveSpecialCharacterException, KairoException{
        // role
        PlayerRole role = dbVerifyUtils.isHasRole(sc.getGameId(),sc.getPlayerId(),sc.getRoleId());
        if(role == null){
            throw new NoSuchRoleException("指定的玩家不存在,roleId="+sc.getRoleId());
        }
        Date now = new Date();
        if(StringUtils.isBlank(objKey)){
            objKey = MD5Util.me().md5Hex(now.getTime() + StringUtils.getUniqueID(8));
        }
        // "/"校验
        if(objKey.matches(".*/.*")){
            throw new IsHaveSpecialCharacterException("Oss 上传文件名太长");
        }
        // key设置
        objKey = role.getChannelId() + "/" + sc.getRoleId() + "/" + role.getGameId() + "/" + objKey;
        if(objKey.length() > 64){
            throw new AdmiralNameIsTooLongException("Oss 上传文件名太长");
        }
        PlayerGameOss playerGameOss = new PlayerGameOss();
        playerGameOss.setRoleId(sc.getRoleId());
        playerGameOss.setGameId(sc.getGameId());
        playerGameOss.setObjKey(objKey);
        playerGameOss.setBucket(getProperty(AppConfig.ALI_OSS_BUCKET, BUCKET_KEY));
        playerGameOss = dbVerifyUtils.isHasOssObjKey(playerGameOss);
        // cch相关（注释）（1）
//        GameServerChannelHandler cch = GameServerSendService.getInstance().GetPlayerOnlineById(roleId);
//        if(cch == null) {
//            playerGameOss = playerGameOssMapper.selectByRoleIdAndObjKey(roleId, getProperty(AppConfig.ALI_OSS_BUCKET, BUCKET_KEY), objKey);
//        } else {
//            playerGameOss = cch.getPlayerGameOssByType(objType);
//        }

        // objkey检测
        if(playerGameOss == null){
            playerGameOss = new PlayerGameOss();

            playerGameOss.setCreateTime(now);
            playerGameOss.setBucket(getProperty(AppConfig.ALI_OSS_BUCKET, BUCKET_KEY));

            playerGameOss.setRoleId(sc.getRoleId());
            playerGameOss.setObjKey(objKey);
        }else{
            // type check
            if(objType != playerGameOss.getObjType()){
                throw new KairoException(KairoErrorCode.SAVE_DATA_TYPE_ERROR);
            }
        }

        playerGameOss.setUpdateTime(now);
        playerGameOss.setStatus(UPLOADING);

        playerGameOss.setGold(gold);
        playerGameOss.setSaveTime(saveTime);
        playerGameOss.setFname(fname);
        playerGameOss.setObjType(objType);

        if(playerGameOss.getId() == null) {
            //playerGameOss.setBackupOssTime(now);
            playerGameOss.setMetaMd5("");
            playerGameOss.setEndTime(null);
            playerGameOss.setGameId(sc.getGameId());

            playerGameOssMapper.insert(playerGameOss);
        }else{
            playerGameOssMapper.updateByPrimaryKeySelective(playerGameOss);
        }


        playerGameOss = playerGameOssMapper.selectByRoleIdAndObjKey(playerGameOss);
//        playerGameOss = dbVerifyUtils.isHasOssGame(sc.getGameId(),sc.getRoleId());

        // 缓存更新（注释）
        /*if(cch != null){
            cch.setPlayerGameOssByType(objType, playerGameOss);
        }*/

        AliOssBucketVO aliOssBucket = new AliOssBucketVO();
        aliOssBucket.setBucket(getProperty(AppConfig.ALI_OSS_BUCKET, BUCKET_KEY));
        aliOssBucket.setObjkey(objKey);
        aliOssBucket.setOperateId(playerGameOss.getId());
        aliOssBucket.setGameId(role.getGameId());
        aliOssBucket.setMetaMd5("");
        aliOssBucket.setType(objType);
        return returnVo.putBucket(aliOssBucket,playerGameOss);
    }

    public void setPlayerRoleOssFile(long roleId,String gameId) {
        // 玩家信息取得
        PlayerRole role = dbVerifyUtils.isHasRole(gameId,null,roleId);
        if(role == null) {
            return;
        }
        // 游戏信息取得(只有game type 为1时)
        Game game = gameMapper.selectByGameId(role.getGameId());
        if(game == null || game.getType() != 1) {
            return;
        }
        OSSClient client = null;
        try {
            if(C_Player_Accesskey_Map.get(roleId) == null || C_Player_Accesskey_Map.get(roleId).get("write") == null){
                // 重新获得key
                AliOssAccessKeyVO roleAccessKey = getOssStsToken(roleId, role.getGameId(), "write", role.getChannelId());
                client = new OSSClient(getProperty(AppConfig.ALI_OSS_ENDPOINT_INTERNAL, ENDPOINT),
                        getProperty(AppConfig.ALI_OSS_ACCESS_KEY, ACCESS_KEY),
                        getProperty(AppConfig.ALI_OSS_ACCESS_SECRET, ACCESS_KEY_SECRET));
            }else{
                // 用户删除access token取得
                AliOssAccessKeyVO roleAccessKey = C_Player_Accesskey_Map.get(roleId).get("write");
                client = new OSSClient(getProperty(AppConfig.ALI_OSS_ENDPOINT_INTERNAL, ENDPOINT),
                        getProperty(AppConfig.ALI_OSS_ACCESS_KEY, ACCESS_KEY),
                        getProperty(AppConfig.ALI_OSS_ACCESS_SECRET, ACCESS_KEY_SECRET));

            }

            // 获取文件，玩家存档文件取得
            List<PlayerGameOss>  playerGameOss = null;
            playerGameOss = dbVerifyUtils.isHasOss(gameId,roleId);

            // 相关代码，之后修改（注释）（1）
            /*GameServerChannelHandler cch  = GameServerSendService.getInstance().GetPlayerOnlineById(roleId);*/
            /*if(cch == null) {
                playerGameOss = playerGameOssMapper.selectByRoleId(roleId);
            }else{
                playerGameOss = new ArrayList<PlayerGameOss>(cch.getPlayerGameOss().values());
            }*/

            if(playerGameOss == null || playerGameOss.size() <= 0) {
                logger.error("============>setPlayerRoleOssFile|===================7");;
                return;
            } else {
                // 一小时以上备份玩家存档
                long subTime = 0L;
                Date now = Calendar.getInstance().getTime();
                String backUpOurs = getProperty(AppConfig.ALI_OSS_BACKUP_TIME, "1");
                String ossBackKeySuffix = UtilDate.date2Text(now, "yyyy-MM-dd HH:mm:ss");
                for(PlayerGameOss oss : playerGameOss) {
                    if(oss.getBackupOssTime() == null) {
                        subTime = Calendar.getInstance().getTimeInMillis();
                    }else{
                        subTime = Calendar.getInstance().getTimeInMillis() - oss.getBackupOssTime().getTime();
                    }
                    if(subTime > StringUtils.getInt(backUpOurs) * 3600 * 1000L) {
                        client.copyObject(getProperty(AppConfig.ALI_OSS_BUCKET, BUCKET_KEY), oss.getObjKey(),
                                getProperty(AppConfig.ALI_OSS_BUCKET_BACK, BUCKET_KEY_BACK), oss.getObjKey()+"_"+ossBackKeySuffix);
                        // 更新数据库
                        PlayerGameOss newOss = new PlayerGameOss();
                        newOss.setId(oss.getId());
                        newOss.setBackupOssTime(now);
                        oss.setBackupOssTime(now);
                        playerGameOssMapper.updateByPrimaryKeySelective(newOss);
                        // 缓存更新 （注释）
                        /*if(cch != null){
                            cch.setPlayerGameOssByType(oss.getObjType(), oss);
                        }*/
                    }
                }
            }
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode() + "|method=setPlayerRoleOssFile");
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());

        } catch (Exception e) {
            System.out.println("err msg:           " + e.getMessage());
        } finally {
            if(client != null){
                client.shutdown();
            }
        }
    }

    private String getProperty(String key, String defaultVal){
        String val = serverConfig.getProperty(key, defaultVal);
        if(val == null || val.trim().length() <= 0) {
            return defaultVal;
        } else {
            return val;
        }
    }

    private AliOssAccessKeyVO getOssStsToken(long roleId, String gameId, String type, String channelId){
        // RoleArn 需要在 RAM 控制台上获取
        String roleArn = getProperty(AppConfig.ALI_OSS_ROLE_READ, "acs:ram::1409892411229494:role/kairogamedatareadonly");
        if("write".equalsIgnoreCase(type) || "delete".equalsIgnoreCase(type)){
            roleArn = getProperty(AppConfig.ALI_OSS_ROLE_WRITE, "acs:ram::1409892411229494:role/kairogamedatawriteonly");
        }
        // RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
        // 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '.' '@' 字母和数字等字符
        // 具体规则请参考API文档中的格式要求
        String roleSessionName = getProperty(AppConfig.ALI_OSS_ROLE_NAME, "kairo-game-data");
        String policy = GET_POLICY;
        if("write".equalsIgnoreCase(type)){
            policy = WRITE_POLICY.replace("{p3}", channelId).replace("{p1}", roleId+"").replace("{p2}", gameId+"").replace("{p0}", getProperty(AppConfig.ALI_OSS_BUCKET, BUCKET_KEY));
        }else if("delete".equalsIgnoreCase(type)){
            policy = DELETE_POLICY.replace("{p3}", channelId).replace("{p2}", gameId+"").replace("{p0}", getProperty(AppConfig.ALI_OSS_BUCKET, BUCKET_KEY));
        }else{
            policy = GET_POLICY.replace("{p0}", getProperty(AppConfig.ALI_OSS_BUCKET, BUCKET_KEY));
        }
        // 此处必须为 HTTPS
        ProtocolType protocolType = ProtocolType.HTTPS;
        long now = Calendar.getInstance().getTimeInMillis();
        try {
            final AssumeRoleResponse response = assumeRole(getProperty(AppConfig.ALI_OSS_RAM_ACCESS_KEY,RAM_ACCESS_KEY),
                    getProperty(AppConfig.ALI_OSS_RAM_ACCESS_SECRET, RAM_ACCESS_KEY_SECRET),
                    roleArn, roleSessionName, policy, protocolType);
            AliOssAccessKeyVO accessKey = new AliOssAccessKeyVO();
            accessKey.setExpiration(response.getCredentials().getExpiration());
            accessKey.setKeyId(response.getCredentials().getAccessKeyId());
            accessKey.setKeySecret(response.getCredentials().getAccessKeySecret());
            accessKey.setToken(response.getCredentials().getSecurityToken());
            accessKey.setEndPoint(getProperty(AppConfig.ALI_OSS_ENDPOINT, ENDPOINT));
            if("write".equalsIgnoreCase(type)){
                accessKey.setKeyType(2);
            }else if("delete".equalsIgnoreCase(type)){
                accessKey.setKeyType(3);
            }else{
                accessKey.setKeyType(1);
            }
            accessKey.setUpdateTime(new Date());
            accessKey.setGameId(gameId);
            accessKey.setChannelId(channelId);
            if(C_Player_Accesskey_Map.get(roleId) == null){
                C_Player_Accesskey_Map.put(roleId, new ConcurrentHashMap<String, AliOssAccessKeyVO>());
            }
            C_Player_Accesskey_Map.get(roleId).put(type, accessKey);
            System.out.println("end:"+(Calendar.getInstance().getTimeInMillis()-now));
            return accessKey;
        } catch (ClientException e) {
            logger.error("AliyunOssStsService|method=getOssStsToken|errcode="+e.getErrCode()+"|errmsg="+e.getErrMsg());
        }
        return null;
    }

    private AssumeRoleResponse assumeRole(String accessKeyId, String accessKeySecret, String roleArn, String roleSessionName, String policy, ProtocolType protocolType) throws ClientException
    {
        try {
            // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
            IClientProfile profile  = DefaultProfile.getProfile(getProperty(AppConfig.ALI_OSS_REGION, REGION_CN_HANGZHOU), accessKeyId, accessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);
            // 创建一个 AssumeRoleRequest 并设置请求参数
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setVersion(STS_API_VERSION);
            request.setMethod(MethodType.POST);
            request.setProtocol(protocolType);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy);
            String duration = getProperty(AppConfig.ALI_OSS_DURATION, DURATION_SECONDS+"");
            request.setDurationSeconds(60L * StringUtils.getInt(duration));// 单位是秒，最小为 900，最大为 3600。
            // 发起请求，并得到response
            final AssumeRoleResponse response = client.getAcsResponse(request);
            return response;
        } catch (ClientException e) {
            throw e;
        }
    }

    private static String GET_POLICY = "{\n" +
            "    \"Version\": \"1\", \n" +
            "    \"Statement\": [\n" +
            "        {\n" +
            "            \"Action\": [\n" +
            "                \"oss:ListObjects\", \n" +
            "                \"oss:GetObject\"\n" +
            "            ], \n" +
            "            \"Resource\": [\n" +
            "                \"acs:oss:*:*:{p0}\", \n" +
            "                \"acs:oss:*:*:{p0}/*\" \n" +
            "            ], \n" +
            "            \"Effect\": \"Allow\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    private static String WRITE_POLICY = "{\n" +
            "    \"Version\": \"1\", \n" +
            "    \"Statement\": [\n" +
            "        {\n" +
            "            \"Action\": [\n" +
            "                \"oss:PutObject\" \n" +
            "            ], \n" +
            "            \"Resource\": [\n" +
            "                \"acs:oss:*:*:{p0}/{p3}/{p1}/{p2}/*\" \n" +
            "            ], \n" +
            "            \"Effect\": \"Allow\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    private static String DELETE_POLICY = "{\n" +
            "    \"Version\": \"1\", \n" +
            "    \"Statement\": [\n" +
            "        {\n" +
            "            \"Action\": [\n" +
            "                \"oss:DeleteObject\" \n" +
            "            ], \n" +
            "            \"Resource\": [\n" +
            "                \"acs:oss:*:*:{p0}/{p3}/{p1}/{p2}/*\" \n" +
            "            ], \n" +
            "            \"Effect\": \"Allow\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";
}