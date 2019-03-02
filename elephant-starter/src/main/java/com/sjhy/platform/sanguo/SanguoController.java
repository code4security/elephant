package com.sjhy.platform.sanguo;

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

    @RequestMapping(value = "/merchandises", method = RequestMethod.GET)
    public ResultDTO<List<PayGoods>> getMerchandises(@RequestParam String gameId,
                                                     @RequestParam String channelId) {
        List<PayGoods> goodsList = payGoodsMapper.selectByGoods(channelId,gameId);
        return ResultDTO.getSuccessResult(goodsList);
    }
}
