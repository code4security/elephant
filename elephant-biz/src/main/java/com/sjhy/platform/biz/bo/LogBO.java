package com.sjhy.platform.biz.bo;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.sjhy.platform.biz.redis.RedisServiceImpl;
import com.sjhy.platform.biz.utils.DbVerifyUtils;
import com.sjhy.platform.biz.utils.SaveLogUtil;
import com.sjhy.platform.biz.utils.StringUtils;
import com.sjhy.platform.biz.utils.UtilDate;
import com.sjhy.platform.client.deploy.config.AppConfig;
import com.sjhy.platform.client.deploy.enumerate.MdLogEnum;
import com.sjhy.platform.client.dto.player.PlayerGameOss;
import com.sjhy.platform.client.dto.player.PlayerRole;
import com.sjhy.platform.client.dto.vo.AccountVO;
import org.springframework.stereotype.Service;

/*
 * 服务器埋点日志
 * @author zhangshang109
 *
 */

@Service("LogBO")
public class LogBO {
    @Resource 
    private Properties serverConfig;
    @Resource
    private RedisServiceImpl redisService;
    @Resource
    private DbVerifyUtils dbVerifyUtils;
    private static final char delimiterBase = 0x01;
    private static final String delimiter   = "" + delimiterBase;// 日志开关
    private static final boolean IsOpenLog  = true;// 日志开关
    private static String logFilePath       = null;
    private static String serverId          = null;
    // 文件写入处理队列
    private ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();

    @PostConstruct
    public void init(){
        // 启动日志管理线程
        ExecutorService pool = Executors.newSingleThreadExecutor();

        pool.execute(new MdLogFileHandle());
    }

    /*
     * 服务器事件埋点
     * @param fileName
     * @param context
     */
    private void saveServerEventLog(String fileName, String context){
        Date date = Calendar.getInstance().getTime();

        if(logFilePath == null){
            logFilePath = serverConfig.getProperty(AppConfig.SERVER_LOG_PATH);
        }

        if(serverId == null){
            serverId = serverConfig.getProperty(AppConfig.SERVER_ID);
        }

        fileName  = fileName + serverId + "." + UtilDate.date2Text(date, UtilDate.DATE_PATTERN);

        //SaveLogUtil.writeFile(logFilePath, fileName, context);

        queue.add(fileName + "|" + context);
    }

    /*
     * 登录流程日志
     * @param deviceId
     * @param accountName
     * @param channelId
     * @param gameId
     * @param code
     */
    public void setMdEventLog(String deviceId, String chUserId, String userId, String channelId, Integer gameId, String code) {
        if(!IsOpenLog)
            return;

        String now = UtilDate.date2Text(Calendar.getInstance().getTime(), UtilDate.DATETIME_PATTERN);

        StringBuilder context = new StringBuilder();

        context.append(now).append(delimiter);
        context.append(channelId).append(delimiter);
        context.append(deviceId).append(delimiter);

        context.append(gameId==null?"null":gameId).append(delimiter);// gameid
        context.append(serverConfig.getProperty(AppConfig.SERVER_ID, "null")).append(delimiter);// serverid

        context.append(chUserId).append(delimiter);
        context.append(userId).append(delimiter);
        context.append(code);

        saveServerEventLog("serverevent.log.", context.toString());
    }

    /*
     * 设定服务器日志事件对象
     * @param 用户ID
     * @param code
     */
    public void setMdEventLog(long userId, Integer gameId, String code){
        if(!IsOpenLog)
            return;

        AccountVO account = redisService.getByUserId(userId, gameId);

        String now = UtilDate.date2Text(Calendar.getInstance().getTime(), UtilDate.DATETIME_PATTERN);

        StringBuilder context = new StringBuilder();

        if(account == null){
            return ;
        }else{
            context.append(now).append(delimiter);
            context.append(account.getChannelId()).append(delimiter);
            context.append(account.getDeviceUniquelyCode()).append(delimiter);

            context.append(gameId==null?"null":gameId).append(delimiter);// gameid
            context.append(serverConfig.getProperty(AppConfig.SERVER_ID, "null")).append(delimiter);// serverid

            context.append(account.getChUserId()).append(delimiter);
            context.append(userId).append(delimiter);
            context.append(code);

            saveServerEventLog("serverevent.log.", context.toString());
        }
    }

    /*
     * 玩家登录，login服务器
     * @param roleId
     * @param eventId
     */
    public void saveLoginLog(String channelId, String chUserId, String userId, Integer gameId, String lastLoginIp){
        if(!IsOpenLog)
            return;

        String now = UtilDate.date2Text(Calendar.getInstance().getTime(), UtilDate.DATETIME_PATTERN);

        StringBuilder context = new StringBuilder();

        context.append(now).append(delimiter);
        context.append("login").append(delimiter);
        context.append("A1000").append(delimiter);
        context.append(gameId==null?"null":gameId).append(delimiter);// gameid
        context.append(serverConfig.getProperty(AppConfig.SERVER_ID, "null")).append(delimiter);// serverid

        context.append(channelId).append(delimiter);
        context.append(chUserId).append(delimiter);
        context.append(userId).append(delimiter);
        context.append(lastLoginIp);

        saveServerEventLog("login.log.", context.toString());
    }

    /*
     * 角色登录
     * @param roleId
     *
     */
    public void setRoleLoginLog(PlayerRole playerRoleVO){
        if(!IsOpenLog)
            return;

        // 玩家信息取得
        //PlayerRoleVO playerRoleVO = playerRoleService.getByPrimaryKey(roleId);

        String now = UtilDate.date2Text(Calendar.getInstance().getTime(), UtilDate.DATETIME_PATTERN);

        StringBuilder context = new StringBuilder();

        if(playerRoleVO == null){
            return ;
        }else{
            AccountVO account = redisService.getByUserId(playerRoleVO.getPlayerId(), Integer.valueOf(playerRoleVO.getGameId()));

            if(account != null){
                context.append(now).append(delimiter);
                context.append("rolelogin").append(delimiter);
                context.append("A2000").append(delimiter);
                context.append(playerRoleVO.getGameId()).append(delimiter);
                context.append(serverConfig.getProperty(AppConfig.SERVER_ID, "null")).append(delimiter);// serverid

                context.append(account.getChannelId()).append(delimiter);
                context.append(account.getChUserId()).append(delimiter);
                context.append(playerRoleVO.getRoleId()).append(delimiter);
                context.append(playerRoleVO.getRoleName()).append(delimiter);
                context.append(playerRoleVO.getFriendid()).append(delimiter);

                context.append(UtilDate.date2Text(playerRoleVO.getCreateTime(), UtilDate.DATETIME_PATTERN)).append(delimiter);
                context.append(UtilDate.date2Text(playerRoleVO.getLastLoginTime(), UtilDate.DATETIME_PATTERN)).append(delimiter);

                saveServerEventLog("rolelogin.log.", context.toString());
            }
        }
    }

    /*
     * 创建角色
     * @param roleId
     *
     */
    public void setRoleBuildLog(PlayerRole playerRoleVO){
        if(!IsOpenLog)
            return;

        // 玩家信息取得
        //PlayerRoleVO playerRoleVO = playerRoleService.getByPrimaryKey(roleId);

        String now = UtilDate.date2Text(Calendar.getInstance().getTime(), UtilDate.DATETIME_PATTERN);

        StringBuilder context = new StringBuilder();

        if(playerRoleVO == null){
            return ;
        }else{
            AccountVO account = redisService.getByUserId(playerRoleVO.getPlayerId(), Integer.valueOf(playerRoleVO.getGameId()));

            if(account != null){
                context.append(now).append(delimiter);
                context.append("rolebuild").append(delimiter);
                context.append("A3000").append(delimiter);
                context.append(playerRoleVO.getGameId()).append(delimiter);
                context.append(serverConfig.getProperty(AppConfig.SERVER_ID, "null")).append(delimiter);// serverid

                context.append(account.getChannelId()).append(delimiter);
                context.append(account.getChUserId()).append(delimiter);
                context.append(playerRoleVO.getRoleId()).append(delimiter);
                context.append(playerRoleVO.getRoleName()).append(delimiter);
                context.append(playerRoleVO.getFriendid()).append(delimiter);

                context.append(UtilDate.date2Text(playerRoleVO.getCreateTime(), UtilDate.DATETIME_PATTERN)).append(delimiter);
                context.append(UtilDate.date2Text(playerRoleVO.getLastLoginTime(), UtilDate.DATETIME_PATTERN)).append(delimiter);

                saveServerEventLog("rolebuild.log.", context.toString());
            }
        }
    }

//    /*
//     * 心跳日志
//     * @param roleId
//     * @param taskId
//     * @param result
//     */
//    public void setHeartLog(){
//        if(!IsOpenLog)
//            return;
//
//        String now = UtilDate.date2Text(Calendar.getInstance().getTime(), UtilDate.DATETIME_PATTERN);
//
//        StringBuilder context = new StringBuilder();
//
//        context.append(now).append(delimiter);
//
//        // 在线人数
//        int onLineUserNum = gameServerSendService.getAllOnlineUser().size();
//        context.append("heart").append(delimiter);
//        context.append("A4000").append(delimiter);
//        context.append(serverConfig.getProperty(AppConfig.SERVER_ID, "null")).append(delimiter);// serverid
//        context.append(onLineUserNum);
//
//        saveServerEventLog("heart.log.", context.toString());
//    }

    /*
     * 退出服务器
     * @param roleId
     *
     */
    public void setLogOutLog(PlayerRole playerRoleVO){
        if(!IsOpenLog)
            return;

        // 玩家信息取得
        //PlayerRoleVO playerRoleVO = playerRoleService.getByPrimaryKey(roleId);

        String now = UtilDate.date2Text(Calendar.getInstance().getTime(), UtilDate.DATETIME_PATTERN);

        StringBuilder context = new StringBuilder();

        if(playerRoleVO == null){
            return ;
        }else{
            AccountVO account = redisService.getByUserId(playerRoleVO.getPlayerId(), Integer.parseInt(playerRoleVO.getGameId()));

            if(account != null) {
                context.append(now).append(delimiter);

                context.append("logout").append(delimiter);
                context.append("A5000").append(delimiter);
                context.append(playerRoleVO.getGameId()).append(delimiter);
                context.append(serverConfig.getProperty(AppConfig.SERVER_ID, "null")).append(delimiter);// serverid

                context.append(account.getChannelId()).append(delimiter);
                context.append(account.getChUserId()).append(delimiter);
                context.append(playerRoleVO.getRoleId()).append(delimiter);
                context.append(playerRoleVO.getRoleName()).append(delimiter);

                context.append(UtilDate.date2Text(playerRoleVO.getCreateTime(), UtilDate.DATETIME_PATTERN)).append(delimiter);
                context.append(UtilDate.date2Text(playerRoleVO.getLastLoginTime(), UtilDate.DATETIME_PATTERN)).append(delimiter);

                long onlinetimes = Calendar.getInstance().getTime().getTime() - playerRoleVO.getLastLoginTime().getTime();

                context.append(onlinetimes/1000);// 在线时间

                saveServerEventLog("logout.log.", context.toString());
            }
        }
    }

    /*
     * 记录邮件盗刷现象
     * @param roleId
     *
     */
    public void setMailErrorLog(PlayerRole playerRoleVO, long mailId){
        if(!IsOpenLog)
            return;

        // 玩家信息取得
        //PlayerRoleVO playerRoleVO = playerRoleService.getByPrimaryKey(roleId);

        String now = UtilDate.date2Text(Calendar.getInstance().getTime(), UtilDate.DATETIME_PATTERN);

        StringBuilder context = new StringBuilder();

        if(playerRoleVO == null){
            return ;
        }else{
            AccountVO account = redisService.getByUserId(playerRoleVO.getPlayerId(), Integer.valueOf(playerRoleVO.getGameId()));

            if(account != null) {
                context.append(now).append(delimiter);

                context.append("mail").append(delimiter);
                context.append("E1000").append(delimiter);
                context.append(playerRoleVO.getGameId()).append(delimiter);
                context.append(serverConfig.getProperty(AppConfig.SERVER_ID, "null")).append(delimiter);// serverid

                context.append(account.getChannelId()).append(delimiter);
                context.append(account.getChUserId()).append(delimiter);
                context.append(playerRoleVO.getRoleId()).append(delimiter);
                context.append(playerRoleVO.getRoleName()).append(delimiter);

                context.append(mailId);// 在线时间

                saveServerEventLog("mail.err.log.", context.toString());
            }
        }
    }

    /*
     * 上传埋点日志
     * @param roleId
     * @param type
     * @param body
     */
    public void saveRechargeLog(long roleId,String gameId, String goodsId, String channelId, String orderId){
        if(!IsOpenLog)
            return;

        // 玩家信息取得
        PlayerRole playerRoleVO = dbVerifyUtils.isHasRole(gameId,null,roleId);

        String now = UtilDate.date2Text(Calendar.getInstance().getTime(), UtilDate.DATETIME_PATTERN);

        StringBuilder context = new StringBuilder();

        if(playerRoleVO == null) {
            return ;
        } else {
            AccountVO account = redisService.getByUserId(playerRoleVO. getPlayerId(), Integer.valueOf(playerRoleVO.getGameId()));

            if(account != null) {
                context.append(now).append(delimiter);

                context.append("recharge").append(delimiter);
                context.append("A6000").append(delimiter);
                context.append(playerRoleVO.getGameId()).append(delimiter);
                context.append(serverConfig.getProperty(AppConfig.SERVER_ID, "null")).append(delimiter);// serverid

                context.append(account.getChannelId()).append(delimiter);
                context.append(account.getChUserId()).append(delimiter);
                context.append(roleId).append(delimiter);
                context.append(playerRoleVO.getRoleName()).append(delimiter);

                context.append(goodsId).append(delimiter);
                context.append(channelId).append(delimiter);
                context.append(orderId);

                saveServerEventLog("recharge.log.", context.toString());
            }
        }
    }

    /*
     * 上传存档日志
     * @param roleId
     * @param type
     * @param body
     */
    public void saveAliOssLog(long roleId,String gameId, PlayerGameOss playerGameOss){
        if(!IsOpenLog)
            return;

        // 玩家信息取得
        PlayerRole playerRoleVO = dbVerifyUtils.isHasRole(gameId,null, roleId);

        String now = UtilDate.date2Text(Calendar.getInstance().getTime(), UtilDate.DATETIME_PATTERN);

        StringBuilder context = new StringBuilder();

        if(playerRoleVO == null) {
            return ;
        } else {
            AccountVO account = redisService.getByUserId(playerRoleVO.getPlayerId(), Integer.valueOf(playerRoleVO.getGameId()));

            if(account != null) {
                context.append(now).append(delimiter);

                context.append("alioss").append(delimiter);
                context.append("A7000").append(delimiter);
                context.append(playerRoleVO.getGameId()).append(delimiter);
                context.append(serverConfig.getProperty(AppConfig.SERVER_ID, "null")).append(delimiter);// serverid

                context.append(account.getChannelId()).append(delimiter);
                context.append(account.getChUserId()).append(delimiter);
                context.append(roleId).append(delimiter);
                context.append(playerRoleVO.getRoleName()).append(delimiter);

                context.append(playerGameOss.getBucket()).append(delimiter);
                context.append(playerGameOss.getObjType()).append(delimiter);
                context.append(playerGameOss.getObjKey()).append(delimiter);
                context.append(playerGameOss.getGold()).append(delimiter);
                context.append(playerGameOss.getMetaMd5()).append(delimiter);
                context.append(playerGameOss.getFname()).append(delimiter);
                context.append(playerGameOss.getSaveTime()).append(delimiter);
                context.append(playerGameOss.getStatus());

                saveServerEventLog("alioss.log.", context.toString());
            }
        }
    }

    /*
     * 上传埋点日志
     * @param roleId
     * @param type
     * @param body
     */
    public void saveMdlog(long roleId,String gameId, String type, String body){
        if(!IsOpenLog)
            return;

        // 不记录这两个埋点文件
        if(type.equals(MdLogEnum.ITEM.getMdType()+"")) {
            return;
        }else if(type.equals(MdLogEnum.TOKEN_MONEY.getMdType()+"")){
            return;
        }

        // 玩家信息取得
        PlayerRole playerRoleVO = dbVerifyUtils.isHasRole(gameId,null,roleId);

        String now = UtilDate.date2Text(Calendar.getInstance().getTime(), UtilDate.DATETIME_PATTERN);

        StringBuilder context = new StringBuilder();

        if(playerRoleVO == null) {
            return ;
        } else {
            AccountVO account = redisService.getByUserId(playerRoleVO.getPlayerId(), Integer.valueOf(playerRoleVO.getGameId()));

            if(account != null) {
                context.append(now).append(delimiter);

                String mdLogName = MdLogEnum.valueOf(StringUtils.getInt(type)).getMdLogName();

                context.append(mdLogName).append(delimiter);
                context.append("B"+type).append(delimiter);
                context.append(playerRoleVO.getGameId()).append(delimiter);
                context.append(serverConfig.getProperty(AppConfig.SERVER_ID, "null")).append(delimiter);// serverid

                context.append(account.getChannelId()).append(delimiter);
                context.append(account.getChUserId()).append(delimiter);
                context.append(roleId).append(delimiter);
                context.append(playerRoleVO.getRoleName()).append(delimiter);

                context.append(body);

                saveServerEventLog(mdLogName + ".log.", context.toString());
            }
        }
    }

    class MdLogFileHandle implements Runnable{

        @Override
        public void run() {
            synchronized (queue) {
                while(true) {

                    if(queue != null && !queue.isEmpty()){
                        String log = queue.poll();

                        if(!"".equals(log)){
                            String[] logInfo = log.split("\\|");

                            if(logInfo.length > 0){
                                SaveLogUtil.writeFile(logFilePath, logInfo[0], logInfo[1]);
                            }
                        }
                    }

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        long onlinetimes = 14400000l;
        System.out.println(onlinetimes/1000);
    }
}

