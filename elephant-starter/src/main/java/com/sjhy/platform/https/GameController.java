package com.sjhy.platform.https;

import com.sjhy.platform.client.deploy.config.IosCode;
import com.sjhy.platform.client.deploy.config.IosMsg;
import com.sjhy.platform.biz.redis.RedisUtil;
import com.sjhy.platform.biz.utils.DbVerifyUtils;
import com.sjhy.platform.biz.utils.StringUtils;
import com.sjhy.platform.client.deploy.config.ModuleConfig;
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.fixed.GiftCode;
import com.sjhy.platform.client.dto.game.*;
import com.sjhy.platform.client.dto.history.PlayerGiftLog;
import com.sjhy.platform.client.dto.player.PlayerBanList;
import com.sjhy.platform.client.dto.player.PlayerIos;
import com.sjhy.platform.client.dto.vo.newGame.ResultVo;
import com.sjhy.platform.persist.mysql.fixed.GiftCodeMapper;
import com.sjhy.platform.persist.mysql.game.*;
import com.sjhy.platform.persist.mysql.history.PlayerGiftLogMapper;
import com.sjhy.platform.persist.mysql.player.PlayerBanListMapper;
import com.sjhy.platform.persist.mysql.player.PlayerIosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by @author xusiyuan on 2019/03/02.
 * <p>
 */
@RestController
@RequestMapping("/game")
public class GameController {
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private RedisUtil redis;
    @Autowired
    private PlayerGiftLogMapper playerGiftLogMapper;
    @Autowired
    private PayGoodsMapper payGoodsMapper;
    @Autowired
    private PlayerIosMapper playerIosMapper;
    @Autowired
    private PlayerBanListMapper banListMapper;
    @Autowired
    private DbVerifyUtils dbVerify;
    @Autowired
    private GiftCodeMapper giftCodeMapper;
    @Autowired
    private GameNotifyMapper gameNotifyMapper;
    @Autowired
    private MailMapper mailMapper;
    @Autowired
    private GameContentMapper gameContentMapper;
    @Autowired
    private GiftCodeListMapper giftCodeListMapper;
    @Autowired
    private ResultVo resultVo;
    @Autowired
    private ModuleSwitchMapper moduleSwitchMapper;

    /**
     * 获取全部商品
     *
     * @param gameId
     * @param channelId
     * @return
     */
    /*@RequestMapping(value = "/merchandises", method = RequestMethod.POST)*/
    @PostMapping(value = "/merchandises")
    public ResultDTO<List<PayGoods>> getMerchandises(@RequestParam String gameId, @RequestParam String channelId) {
        // 初始化商品列表
        List<PayGoods> goodsList = new ArrayList<>();
        // 验证客户端参数
        if (dbVerify.isHasGame(gameId) && dbVerify.isHasChannel(channelId, gameId)) {
            // 获取缓存
            String key = gameId + "_" + channelId;
            goodsList = (List<PayGoods>) redis.get(key);
            if (goodsList != null) {
                return ResultDTO.getSuccessResult(IosCode.OK.getErrorCode(), goodsList);
            }
            goodsList = payGoodsMapper.selectByGoods(channelId, gameId);
            // 设置缓存，失效时间
            redis.set(key, goodsList, 30);

            return ResultDTO.getSuccessResult(IosCode.OK.getErrorCode(), goodsList);
        }
        return ResultDTO.getFailureResult(IosCode.ERROR_CLIENT_VALUE.getErrorCode(), IosMsg.ERROR_CLIENT_VALUE.getInnerMsg(), "获取商品失败");
    }

    /**
     * 验证版本
     *
     * @param gameId
     * @param channelId
     * @param version
     * @return
     */
    @PostMapping(value = "/version")
    public ResultDTO<String> VerifyVersion(@RequestParam String gameId, @RequestParam String channelId, @RequestParam float version) {
        try {
            if (dbVerify.isHasChannel(channelId, gameId)) {
                ChannelAndVersion andVersion = (ChannelAndVersion) redis.get("c_+" + channelId);
                if (andVersion == null) {
                    return ResultDTO.getFailureResult(IosCode.ERROR_UNKNOWN.getErrorCode(), IosCode.ERROR_UNKNOWN.getDesc(), "未知异常");
                }
                if (Float.valueOf(andVersion.getVersionNum()) > version) {
                    return ResultDTO.getFailureResult(IosCode.UPDATE_NEW_VERSION.getErrorCode(), andVersion.getVersionDownload(), "版本需要进行更新");
                } else {
                    return ResultDTO.getSuccessResult(IosCode.OK.getErrorCode(), "");
                }
            }
            return ResultDTO.getFailureResult(IosCode.ERROR_CLIENT_VALUE.getErrorCode(), IosCode.ERROR_CLIENT_VALUE.getErrorCode(), "游戏或渠道不存在");
        } catch (Exception e) {
            return ResultDTO.getFailureResult(IosCode.ERROR_UNKNOWN.getErrorCode(), IosCode.ERROR_UNKNOWN.getDesc(), "未知异常");
        }
    }

    /**
     * 开启热更新板块
     *
     * @param gameId
     * @param channelId
     * @return
     */
    @GetMapping(value = "/hotUpdate")
    public ResultDTO<Boolean> HotUpdate(@RequestParam String gameId, @RequestParam String channelId) {
        if (dbVerify.isHasChannel(channelId, gameId)) {
            ModuleSwitch module = moduleSwitchMapper.selectByModule(new ModuleSwitch(null, gameId, channelId, ModuleConfig.HOT_UPDATE.getModule(), null));
            if (module == null) {
                return ResultDTO.getFailureResult(IosCode.ERROR_UNKNOWN.getErrorCode(), IosCode.ERROR_UNKNOWN.getDesc(), "未找到更新模块");
            }
            // 判断如果是0表示不更新，1表示更新
            if (module.getStatus()) {
                return ResultDTO.getSuccessResult(IosCode.OK.getErrorCode(), true);
            } else {
                return ResultDTO.getSuccessResult(IosCode.OK.getErrorCode(), false);
            }
        }
        return ResultDTO.getFailureResult(IosCode.ERROR_CLIENT_VALUE.getErrorCode(), IosCode.ERROR_CLIENT_VALUE.getDesc(), "验证游戏或渠道失败");
    }

    /**
     * 登录
     *
     * @param gameId
     * @param channelId
     * @param userId    设备唯一id
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultDTO<ResultVo> login(@RequestParam String gameId, @RequestParam String channelId, @RequestParam String userId) {
        logger.info("玩家尝试登陆中....");
        PlayerIos playerIos = null;
        // 判断传入的参数是否为空
        if (dbVerify.isHasGame(gameId) && dbVerify.isHasChannel(channelId, gameId) && StringUtils.isNotEmpty(userId)) {
            //查询数据库是否存在改玩家
            playerIos = playerIosMapper.selectByClientId
                    (new PlayerIos(null, gameId, channelId, userId, null, null, null, null, null, null));
            if (playerIos == null) {
                // 查询数据库不存在该玩家，创建新角色
                playerIosMapper.insert
                        (new PlayerIos(null, gameId, channelId, userId, new Date(), new Date(), null, null, null, null));
                // 更新查询
                playerIos = playerIosMapper.selectByClientId
                        (new PlayerIos(null, gameId, channelId, userId, null, null, null, null, null, null));
                // 创建角色物品信息
                GameContent gameContent = new GameContent();
                gameContent.setRoleId(playerIos.getIosId());
                gameContent.setGameId(gameId);
                gameContent.setChannelId(channelId);
                gameContentMapper.insertSelective(gameContent);
            } else {
                // 判断玩家是否被封禁
                if (ban(playerIos.getIosId(), gameId, channelId)) {
                    // 返回true表示没有被封
                    playerIosMapper.updateByPrimaryKeySelective
                            (new PlayerIos
                                    (playerIos.getIosId(), null, null, null, null, new Date(), null, null, null, null));
                } else {
                    return ResultDTO.getFailureResult(IosCode.ERROR_BAN.getErrorCode(), String.valueOf(playerIos.getIosId()), "该账号已经封禁");
                }
            }
            return ResultDTO.getSuccessResult(IosCode.OK.getErrorCode(), resultVo.getLogin(playerIos.getIosId(), playerIos.getMonthlyCard(), playerIos.getAdTime(), playerIos.getPutArchive()));
        }
        return ResultDTO.getFailureResult(IosCode.ERROR_CLIENT_VALUE.getErrorCode(), IosCode.ERROR_CLIENT_VALUE.getDesc(), "登录失败");
    }

    /**
     * 获取服务器当前时间戳
     * @return
     */
    @GetMapping("/serverTime")
    public ResultDTO<String> serverTime(){
        return ResultDTO.getSuccessResult(IosCode.OK.getErrorCode(),String.valueOf(System.currentTimeMillis()));
    }
    @GetMapping("/serverDate")
    public String serverDate(){
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 查询玩家是否被封禁
     *
     * @param iosId
     * @param gameId
     * @param channelId
     * @return
     */
    private Boolean ban(Long iosId, String gameId, String channelId) {
        // 验证玩家是否存在
        if (dbVerify.isHasIos(iosId, gameId, channelId)) {
            // 查询玩家是否已经封禁
            PlayerBanList playerBanList = banListMapper.selectByBan(new PlayerBanList(null, gameId, channelId, iosId, null, null, null));
            // 如果没有改玩家或者已经解封返回true
            if (playerBanList == null || playerBanList.getIsBan() == false) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取公告
     *
     * @return
     */
    @PostMapping("/bulletin")
    public ResultDTO<String> getBulletin(@RequestParam String gameId) {
        if (dbVerify.isHasGame(gameId)) {
            // 获取缓存
            String redisbBull = (String) redis.get(gameId + "_bull");
            if (redisbBull != null) {
                return ResultDTO.getSuccessResult(IosCode.OK.getErrorCode(), redisbBull);
            }
            GameNotify gameNotify = gameNotifyMapper.selectLatestNotify(gameId);
            // 判断是否有公告
            if (gameNotify == null) {
                return ResultDTO.getFailureResult(IosCode.ERROR_NOT_NOTIFY.getErrorCode(), IosCode.ERROR_NOT_NOTIFY.getDesc(), "暂无公告");
            }
            logger.info("发送公告=====【】【】【】======" + gameNotify.getContent());
            // 设置缓存，失效时间
            redis.set(gameId + "_bull", gameNotify.getContent(), 30);
            return ResultDTO.getSuccessResult(IosCode.OK.getErrorCode(), gameNotify.getContent());
        } else {
            return ResultDTO.getFailureResult(IosCode.ERROR_CLIENT_VALUE.getErrorCode(), IosCode.ERROR_CLIENT_VALUE.getDesc(), "获取公告失败");
        }
    }

    /**
     * 获取奖励,使用邮件发送机制
     *
     * @param iosId
     * @param gameId
     * @param channelId
     * @return
     */
    @PostMapping("/gift")
    public ResultDTO<List<Mail>> getGift(@RequestParam Long iosId, @RequestParam String gameId, @RequestParam String channelId, @RequestParam Boolean del, @RequestParam int mailId) {
        Mail mail = new Mail();
        // 玩家存在判断
        if (dbVerify.isHasIos(iosId, gameId, channelId)) {
            // 判断是否需要删除邮件
            if (del == false) {
                // 查询所有未领取奖励邮件
                List<Mail> mailList = mailMapper.selectByRoleId(iosId, gameId, 0, 30);
                if (mailList != null) {
                    // 遍历邮件集合
                    for (int i = 0; i < mailList.size(); i++) {
                        // 邮件状态值判断，如果小于3进行状态值+1.如果大于等于3，直接删除该邮件
                        if (mailList.get(i).getStatus() < 5) {
                            mail.setId(mailList.get(i).getId());
                            mail.setStatus((short) (mailList.get(i).getStatus() + 1));
                            // 需要状态值+1
                            mailMapper.updateByPrimaryKeySelective(mail);
                            // 拼接返回参数 mailId + roleId + goodsId + goodsNum
                            logger.info("===========[][send][mail][]" + mailList.get(i));
                        } else {
                            // 删除邮件
                            mailMapper.deleteByPrimaryKey(mailList.get(i).getId());
                            logger.info("===========[1][delete][mail][]" + mailList.get(i));
                        }
                    }
                    // 更新查询
                    mailList = mailMapper.selectByRoleId(iosId, gameId, 0, 30);
                    // 返回邮件列表
                    return ResultDTO.getSuccessResult(IosCode.OK.getErrorCode(), mailList);
                } else {
                    // 没有奖励
                    return ResultDTO.getSuccessResult(IosCode.OK.getErrorCode(), null);
                }
            } else {
                // 删除邮件
                mailMapper.deleteByPrimaryKey(mailId);
                logger.info("===========[2][delete][mail][]" + mailId);
                return ResultDTO.getSuccessResult(IosCode.OK.getErrorCode(), null);
            }
        } else {
            return ResultDTO.getFailureResult(IosCode.ERROR_CLIENT_VALUE.getErrorCode(), IosCode.ERROR_CLIENT_VALUE.getDesc(), "获取奖励失败");
        }
    }

    /**
     * 获取奖牌
     *
     * @param iosId
     * @param gameId
     * @param channelId
     * @param lastMedal
     * @param freeMedal
     * @param spendMedal
     * @return
     */
    @PostMapping("/money")
    public ResultDTO<Integer> pdateMedal(@RequestParam Long iosId, @RequestParam String gameId, @RequestParam String channelId,
                                         @RequestParam Integer lastMedal, @RequestParam Integer freeMedal, @RequestParam Integer spendMedal, @RequestParam Integer rmbMedal) {
        if (dbVerify.isHasIos(iosId, gameId, channelId)) {
            // 初始化
            GameContent gameContent = new GameContent();
            gameContent.setRoleId(iosId);
            gameContent.setGameId(gameId);
            gameContent.setChannelId(channelId);
            // 查询
            gameContent = gameContentMapper.selectByRole(gameContent);
            gameContent.setLastMedal(lastMedal);
            gameContent.setFreeMedal(freeMedal);
            gameContent.setSpendMedal(spendMedal);
            System.out.println(lastMedal + freeMedal + gameContent.getRmbMedal());
            gameContent.setTotalMedal(lastMedal + freeMedal + gameContent.getRmbMedal());
            // 修改
            gameContentMapper.updateByPrimaryKeySelective(gameContent);
            return ResultDTO.getSuccessResult(IosCode.OK.getErrorCode(), gameContent.getLastMedal());
        }
        return ResultDTO.getFailureResult(IosCode.ERROR_CLIENT_VALUE.getErrorCode(), IosCode.ERROR_CLIENT_VALUE.getDesc(), "获取奖牌失败");
    }

    /**
     * 使用激活码
     *
     * @param iosId
     * @param gameId
     * @param channelId
     * @param giftCode
     * @return
     */
    @PostMapping("/uselipinma")
    public ResultDTO<Map<String, String>> useLPM(@RequestParam Long iosId, @RequestParam String gameId, @RequestParam String channelId, @RequestParam String giftCode) {
        GiftCode codes = giftCodeMapper.selectByCode(giftCode);
        if (codes == null) {
            return ResultDTO.getFailureResult(IosCode.ERROR_GIFT_CODE.getErrorCode(), IosCode.ERROR_GIFT_CODE.getDesc(), "礼品码不存在");
        }
        Integer listId = codes.getGiftListId();
        GiftCodeList giftCodeList = giftCodeListMapper.selectByPrimaryKey(listId);
        //2丶判断礼品码是否存在
        if (giftCodeList == null) {
            return ResultDTO.getFailureResult(IosCode.ERROR_GIFT_CODE.getErrorCode(), IosCode.ERROR_GIFT_CODE.getDesc(), "礼品码不存在");
        }
        //1丶判断玩家是否存在
        if (!dbVerify.isHasIos(iosId, gameId, channelId)) {
            return ResultDTO.getFailureResult(IosCode.ERROR_CLIENT_VALUE.getErrorCode(), IosCode.ERROR_CLIENT_VALUE.getDesc(), "未找到玩家");
            //3丶判断礼品码是否过期  b  a
        } else if (giftCodeMapper.expired(listId, giftCodeList.getBeginTime(), giftCodeList.getEndTime()) == 0) {
            return ResultDTO.getFailureResult(IosCode.ERROR_GIFT_CODE.getErrorCode(), IosCode.ERROR_GIFT_CODE.getDesc(), "礼品码已过期");//3丶判断礼品码是否过期  b  a
        } else {
            //4丶判断礼品码使用log中是否存在数据
            PlayerGiftLog playerGiftLog = new PlayerGiftLog();
            playerGiftLog.setGiftListId(listId);
            playerGiftLog.setRoleId(iosId);
            playerGiftLog.setGameId(gameId);
            playerGiftLog = playerGiftLogMapper.selectByUseGiftCode(playerGiftLog);
            if (playerGiftLog != null) {
                return ResultDTO.getFailureResult(IosCode.ERROR_GIFT_CODE.getErrorCode(), IosCode.ERROR_GIFT_CODE.getDesc(), "用户已使用过礼品码");
            } else {
                //5丶礼品码的产品信息  1#20 2#111  ...等等  修改注册码状态  新加使用记录  向客户端发送结果
                giftCodeMapper.updateByUse(giftCode);

                playerGiftLog = new PlayerGiftLog();
                playerGiftLog.setGiftCode(giftCode);
                playerGiftLog.setGiftListId(listId);
                playerGiftLog.setRoleId(iosId);
                //查询角色ID   没用  默认0 就行
                playerGiftLog.setPlayerId((long) 0);
                playerGiftLog.setGameId(gameId);
                playerGiftLog.setChannelId(channelId);
                playerGiftLog.setActivateTime(new Date());
                playerGiftLogMapper.insertSelective(playerGiftLog);
                Map<String, String> concurrentHashMap = new ConcurrentHashMap<>();
                String[] split = giftCodeList.getGiftRewardId().split("&");
                for (String split2 : split) {
                    concurrentHashMap.put(split2.split("#")[0], split2.split("#")[1]);
                }
                logger.info("激活码已被使用!!!");
                return ResultDTO.getSuccessResult(IosCode.OK.getErrorCode(), concurrentHashMap);
            }

        }
    }

    @PostMapping("/test")
    public String test() {
        return "ok";
    }

}
