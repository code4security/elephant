package com.sjhy.platform.sanguo;

import com.sjhy.platform.biz.deploy.utils.StringUtils;
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.game.PayGoods;
import com.sjhy.platform.persist.mysql.game.PayGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 获取全部商品
     * @param gameId
     * @param channelId
     * @return
     */
    @RequestMapping(value = "/merchandises", method = RequestMethod.GET)
    public ResultDTO<List<PayGoods>> getMerchandises(@RequestParam String gameId,
                                                     @RequestParam String channelId) {

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
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResultDTO<Integer> login(@RequestParam String gameId,
                                    @RequestParam String channelId,
                                    @RequestParam String userId){
        if(StringUtils.isEmpty(gameId) || StringUtils.isEmpty(channelId)){
            return ResultDTO.getSuccessResult(0);
        }

        return null;

    }

}
