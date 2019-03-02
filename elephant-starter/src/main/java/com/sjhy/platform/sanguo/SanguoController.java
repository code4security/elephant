package com.sjhy.platform.sanguo;

import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.game.PayGoods;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author xusiyuan on 2019/03/02.
 * <p>
 */
@RestController
@RequestMapping("/sanguo")
public class SanguoController {

    @RequestMapping(value = "/merchandises", method = RequestMethod.GET)
    public ResultDTO<PayGoods> getMerchandises(@RequestParam String gameId,
                                               @RequestParam String channelId) {
        PayGoods payGoods = new PayGoods();
        payGoods.setChannelId(channelId);
        return ResultDTO.getSuccessResult(payGoods);
    }
}
