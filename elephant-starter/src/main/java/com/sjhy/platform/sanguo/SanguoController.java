package com.sjhy.platform.sanguo;

import com.sjhy.platform.biz.deploy.config.IosCode;
import com.sjhy.platform.biz.deploy.redis.RedisUtil;
import com.sjhy.platform.biz.deploy.utils.DbVerifyUtils;
import com.sjhy.platform.biz.deploy.utils.StringUtils;
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.game.PayGoods;
import com.sjhy.platform.client.dto.player.PlayerIos;
import com.sjhy.platform.persist.mysql.game.PayGoodsMapper;
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

    /**
     * 获取全部商品
     * @param gameId
     * @param channelId
     * @return
     */
    /*@RequestMapping(value = "/merchandises", method = RequestMethod.POST)*/
    @PostMapping(value = "/merchandises")
    public ResultDTO<List<PayGoods>> getMerchandises(@RequestParam String gameId, @RequestParam String channelId) {
        List<PayGoods> goodsList = new ArrayList<>();

        if(DbVerifyUtils.isHasGameId(gameId) && DbVerifyUtils.isHasChannelId(channelId,gameId)){

            goodsList = (List<PayGoods>) redis.get("goods");
            if (goodsList != null){
                return ResultDTO.getSuccessResult(goodsList);
            }

            goodsList = payGoodsMapper.selectByGoods(channelId,gameId);
            redis.set("goods",goodsList);

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
        if(DbVerifyUtils.isHasGameId(gameId) && DbVerifyUtils.isHasChannelId(channelId,gameId) && StringUtils.isNotEmpty(userId)){
            return IosCode.ERROR_CLIENT_VALUE.getErrorCode();
        }
        //查询数据库是否存在userid
        playerIos = playerIosMapper.selectByClientId(new PlayerIos(null,gameId,channelId,userId,null,null));
        if (playerIos == null){// 查询数据库不存在该玩家，创建新角色
            playerIosMapper.insert(new PlayerIos(null,gameId,channelId,userId,new Date(),new Date()));
            // 更新playerChannel内参数
            playerIos = playerIosMapper.selectByClientId(new PlayerIos(null,gameId,channelId,userId,null,null));
        }else {
            playerIosMapper.updateByPrimaryKeySelective(new PlayerIos(playerIos.getIosId(),null,null,null,null,new Date()));
        }
        return IosCode.OK.getErrorCode()+"@"+playerIos.getIosId();
    }

    @RequestMapping(value = "/ban", method = RequestMethod.POST)
    public String ban(@RequestParam Long iosId, @RequestParam String gameId, @RequestParam String channelId){
        PlayerIos playerIos = playerIosMapper.selectByPrimaryKey(iosId);
        if (DbVerifyUtils.isHasGameId(gameId) && DbVerifyUtils.isHasChannelId(channelId,gameId) && StringUtils.isNotEmpty(String.valueOf(playerIos))){
            return IosCode.OK.getErrorCode();
        }
        return IosCode.ERROR_CLIENT_VALUE.getErrorCode();
    }

}
