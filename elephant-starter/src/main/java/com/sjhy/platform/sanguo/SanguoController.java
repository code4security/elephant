package com.sjhy.platform.sanguo;

import com.sjhy.platform.biz.deploy.config.IosCode;
import com.sjhy.platform.biz.deploy.utils.StringUtils;
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.game.PayGoods;
import com.sjhy.platform.client.dto.player.Player;
import com.sjhy.platform.client.dto.player.PlayerChannel;
import com.sjhy.platform.client.dto.player.PlayerIos;
import com.sjhy.platform.persist.mysql.game.PayGoodsMapper;
import com.sjhy.platform.persist.mysql.player.PlayerChannelMapper;
import com.sjhy.platform.persist.mysql.player.PlayerIosMapper;
import com.sjhy.platform.persist.mysql.player.PlayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by @author xusiyuan on 2019/03/02.
 * <p>
 */
@RestController
@RequestMapping("/sanguo")
public class SanguoController {

    @Autowired
    private PayGoodsMapper payGoodsMapper;
    @Autowired
    private PlayerChannelMapper playerChannelMapper;
    @Autowired
    private PlayerIosMapper playerIosMapper;

    /**
     * 获取全部商品
     * @param gameId
     * @param channelId
     * @return
     */
    @RequestMapping(value = "/merchandises", method = RequestMethod.POST)
    public ResultDTO<List<PayGoods>> getMerchandises(@RequestParam String gameId, @RequestParam String channelId) {

        if(StringUtils.isEmpty(gameId) || StringUtils.isEmpty(channelId)){
            return ResultDTO.getSuccessResult(null);
        }

        List<PayGoods> goodsList = payGoodsMapper.selectByGoods(channelId,gameId);
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
        if(StringUtils.isEmpty(gameId) || StringUtils.isEmpty(channelId) || StringUtils.isEmpty(userId)){
            return IosCode.ERROR_UNKNOWN.getErrorCode();
        }
        //查询数据库是否存在userid
        playerIos = playerIosMapper.selectByClientId(new PlayerIos(null,gameId,channelId,userId,null,null));
        if (playerIos == null){// 查询数据库不存在该玩家，创建新角色
            playerIosMapper.insert(new PlayerIos(null,gameId,channelId,userId,new Date(),new Date()));
            // 更新playerChannel内参数
            playerIos = playerIosMapper.selectByClientId(new PlayerIos(null,gameId,channelId,userId,null,null));
        }else {
            playerIosMapper.updateByPrimaryKeySelective(new PlayerIos(null,null,null,null,null,new Date()));
        }
        return IosCode.OK.getErrorCode()+"@"+playerIos.getIosId();
    }

}
