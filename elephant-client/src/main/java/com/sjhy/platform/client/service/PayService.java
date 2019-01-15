package com.sjhy.platform.client.service;

import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.dto.game.PayGoods;
import com.sjhy.platform.client.dto.vo.PayAddOrderVO;
import com.sjhy.platform.client.dto.vo.PayLogVO;
import com.sjhy.platform.client.dto.vo.PayNotifyVO;
import com.sjhy.platform.client.dto.vo.pay.*;

/**
 * @HJ
 */
public interface PayService {
    // 应用宝渠道
    ResultDTO<AddYYBOrderResultVO> addYYBOrder(ServiceContext sc, PayAddOrderVO order, String extra);
    // 腾讯渠道
    ResultDTO<AddTxOrderResultVO> addTxOrder(ServiceContext sc, PayAddOrderVO order, String extra);
    // 360渠道
    ResultDTO<AddQihooOrderResultVO> addQihooOrder(ServiceContext sc, PayAddOrderVO order, String extra);
    // vivo渠道
    ResultDTO<AddVivoOrderResultVO> addVivoOrder(ServiceContext sc, PayAddOrderVO order, String extra);
    // 金立渠道
    ResultDTO<AddJinliOrderResultVO> addJinliOrder(ServiceContext sc, PayAddOrderVO order, String extra);
    // 联想渠道
    ResultDTO<AddLenovoOrderResultVO> addLenovoOrder(ServiceContext sc, PayAddOrderVO order, String extra);
    // 添加支付订单
    ResultDTO<AddOrderResultVO> addOrder(ServiceContext sc, PayAddOrderVO order, String extra);
    // 获取奖牌
    ResultDTO<PayLogVO> getMedal(ServiceContext sc);
    // 是否购买单机商品
    ResultDTO<String[]> isPlayerBuyOfflineGoods(ServiceContext sc, String goodsName);
    // 支付通知
    ResultDTO<Integer> payNotify(ServiceContext sc, PayNotifyVO notify);
}
