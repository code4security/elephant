package com.sjhy.platform.sanguo;

import com.sjhy.platform.biz.deploy.config.IosCode;
import com.sjhy.platform.biz.deploy.redis.RedisUtil;
import com.sjhy.platform.biz.deploy.utils.DbVerifyUtils;
import com.sjhy.platform.biz.deploy.utils.StringUtils;
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.game.GameContent;
import com.sjhy.platform.client.dto.game.GameNotify;
import com.sjhy.platform.client.dto.game.Mail;
import com.sjhy.platform.client.dto.game.PayGoods;
import com.sjhy.platform.client.dto.player.PlayerBanList;
import com.sjhy.platform.client.dto.player.PlayerIos;
import com.sjhy.platform.persist.mysql.game.GameContentMapper;
import com.sjhy.platform.persist.mysql.game.GameNotifyMapper;
import com.sjhy.platform.persist.mysql.game.MailMapper;
import com.sjhy.platform.persist.mysql.game.PayGoodsMapper;
import com.sjhy.platform.persist.mysql.player.PlayerBanListMapper;
import com.sjhy.platform.persist.mysql.player.PlayerIosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by @author xusiyuan on 2019/03/02.
 * <p>
 */
@RestController
@RequestMapping("/sanguo")
public class SanguoController {
    private static final Logger logger = LoggerFactory.getLogger(SanguoController.class);

    @Autowired
    private RedisUtil redis;
    @Autowired
    private PayGoodsMapper payGoodsMapper;
    @Autowired
    private PlayerIosMapper playerIosMapper;
    @Autowired
    private PlayerBanListMapper banListMapper;
    @Autowired
    private DbVerifyUtils dbVerify;
    @Autowired
    private GameNotifyMapper gameNotifyMapper;
    @Autowired
    private MailMapper mailMapper;
    @Autowired
    private GameContentMapper gameContentMapper;

    /**
     * 获取全部商品
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
        if(dbVerify.isHasGame(gameId) && dbVerify.isHasChannel(channelId,gameId)){

            String key = gameId+"_"+channelId;

            goodsList = (List<PayGoods>) redis.get(key);
            if (goodsList != null){
                return ResultDTO.getSuccessResult(goodsList);
            }
            goodsList = payGoodsMapper.selectByGoods(channelId,gameId);
            redis.set(key,goodsList);

            return ResultDTO.getSuccessResult(goodsList);
        }
        return ResultDTO.getSuccessResult(goodsList);
    }

    /**
     * 登录
     * @param gameId
     * @param channelId
     * @param userId 设备唯一id
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String gameId, @RequestParam String channelId, @RequestParam String userId){
        PlayerIos playerIos = null;
        // 判断传入的参数是否为空
        if(dbVerify.isHasGame(gameId) && dbVerify.isHasChannel(channelId,gameId) && StringUtils.isNotEmpty(userId)){
            //查询数据库是否存在userid
            playerIos = playerIosMapper.selectByClientId(new PlayerIos(null,gameId,channelId,userId,null,null,null,null));
            if (playerIos == null){
                // 查询数据库不存在该玩家，创建新角色
                playerIosMapper.insert(new PlayerIos(null,gameId,channelId,userId,new Date(),new Date(),null,null));
                // 更新playerIos参数
                playerIos = playerIosMapper.selectByClientId(new PlayerIos(null,gameId,channelId,userId,null,null,null,null));
            }else {
                // 判断玩家是否被封禁
                if (ban(playerIos.getIosId(),gameId,channelId)){
                    // 返回true表示没有被封
                    playerIosMapper.updateByPrimaryKeySelective(new PlayerIos(playerIos.getIosId(),null,null,null,null,new Date(),null,null));
                }else {
                    return IosCode.ERROR_BAN.getErrorCode()+"@"+playerIos.getIosId();
                }
            }
            return IosCode.OK.getErrorCode()+"@"+playerIos.getIosId();
        }
        return IosCode.ERROR_CLIENT_VALUE.getErrorCode();

    }

    /**
     * 查询玩家是否被封禁
     * @param iosId
     * @param gameId
     * @param channelId
     * @return
     */
    public Boolean ban(Long iosId, String gameId, String channelId){
        if (dbVerify.isHasIos(iosId,gameId,channelId)){
            PlayerBanList playerBanList = banListMapper.selectByBan(new PlayerBanList(null,gameId,channelId,iosId,null,null,null));
            if (playerBanList == null || playerBanList.getIsBan() == false){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    /**
     * 获取公告
     * @return
     */
    @PostMapping("/bulletin")
    public String getBulletin(@RequestParam String gameId){
        if (dbVerify.isHasGame(gameId)){
            GameNotify gameNotify = gameNotifyMapper.selectLatestNotify(gameId);
            if (gameNotify == null){
                return IosCode.OK.getErrorCode()+"@暂无公告";
            }
            logger.info("发送公告=====【】【】【】======"+gameNotify.getContent());
            return IosCode.OK.getErrorCode()+"@"+gameNotify.getContent();
        }else {
            return IosCode.ERROR_CLIENT_VALUE.getErrorCode();
        }
    }

    /**
     * 获取奖励,使用邮件发送机制
     * @param iosId
     * @param gameId
     * @param channelId
     * @return
     */
    @PostMapping("/gift")
    public ResultDTO<List<Mail>> getGift(@RequestParam Long iosId, @RequestParam String gameId, @RequestParam String channelId){
        if (dbVerify.isHasIos(iosId,gameId,channelId)){
            return null;
        }else {
            Mail mail = new Mail();
            mail.setRecvRoleId(iosId);
            mail.setGameId(gameId);
            mail.setChannelId(channelId);
            List<Mail> mailList = mailMapper.selectByRoleId(mail,0,30);
            return ResultDTO.getSuccessResult(mailList);
        }
    }

    /**
     * 获取奖牌
     * @param iosId
     * @param gameId
     * @param channelId
     * @param lastMedal
     * @param rmbMedal
     * @param freeMadel
     * @param spendMedal
     * @param totleMedal
     * @return
     */
    @PostMapping("/medal")
    public String updateMedal(@RequestParam Long iosId, @RequestParam String gameId, @RequestParam String channelId,
                              @RequestParam int lastMedal, @RequestParam int rmbMedal, @RequestParam int freeMadel, @RequestParam int spendMedal, @RequestParam int totleMedal){
        if (dbVerify.isHasIos(iosId,gameId,channelId)){
            GameContent content = new GameContent(null,iosId,gameId,channelId,lastMedal,rmbMedal,freeMadel,spendMedal,totleMedal);
            GameContent gameContent = gameContentMapper.selectByRole(content);
            if (gameContent != null){
                gameContentMapper.updateByPrimaryKeySelective(new GameContent(gameContent.getId(),iosId,gameId,channelId,lastMedal,rmbMedal,freeMadel,spendMedal,totleMedal));
            }else {
                gameContentMapper.insertSelective(content);
            }
            return IosCode.OK.getErrorCode()+"@"+iosId;
        }else {
            return IosCode.ERROR_CLIENT_VALUE.getErrorCode();
        }
    }

    @GetMapping("/test")
    public String test(){
        return "ok";
    }

}
