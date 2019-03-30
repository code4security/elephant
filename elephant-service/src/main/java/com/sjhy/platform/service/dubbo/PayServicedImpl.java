package com.sjhy.platform.service.dubbo;

import com.sjhy.platform.biz.bo.PayBO;
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.deploy.exception.KairoException;
import com.sjhy.platform.client.deploy.exception.NoSuchRoleException;
import com.sjhy.platform.client.dto.vo.PayAddOrderVO;
import com.sjhy.platform.client.dto.vo.PayLogVO;
import com.sjhy.platform.client.dto.vo.PayNotifyVO;
import com.sjhy.platform.client.dto.vo.pay.*;
import com.sjhy.platform.client.service.PayServiced;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.management.relation.RoleNotFoundException;

/**
 * @HJ
 */
@Service(value = "PayServiced")
public class PayServicedImpl implements PayServiced {
    @Resource
    private PayBO payBO;

    @Override
    /**
     * 应用宝
     */
    public ResultDTO<AddYYBOrderResultVO> addYYBOrder(ServiceContext sc, PayAddOrderVO order, String extra) {
        try {
            return ResultDTO.getSuccessResult(payBO.addYYBOrder(sc, order, extra));
        } catch (KairoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * 腾讯
     */
    public ResultDTO<AddTxOrderResultVO> addTxOrder(ServiceContext sc, PayAddOrderVO order, String extra) {
        try {
            return ResultDTO.getSuccessResult(payBO.addTxOrder(sc, order, extra));
        } catch (KairoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * 360
     */
    public ResultDTO<AddQihooOrderResultVO> addQihooOrder(ServiceContext sc, PayAddOrderVO order, String extra) {
        try {
            return ResultDTO.getSuccessResult(payBO.addQihooOrder(sc, order, extra));
        } catch (KairoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * vivo
     */
    public ResultDTO<AddVivoOrderResultVO> addVivoOrder(ServiceContext sc, PayAddOrderVO order, String extra) {
        try {
            return ResultDTO.getSuccessResult(payBO.addVivoOrder(sc, order, extra));
        } catch (KairoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * 金立
     */
    public ResultDTO<AddJinliOrderResultVO> addJinliOrder(ServiceContext sc, PayAddOrderVO order, String extra) {
        try {
            return ResultDTO.getSuccessResult(payBO.addJinliOrder(sc, order, extra));
        } catch (KairoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * 联想
     */
    public ResultDTO<AddLenovoOrderResultVO> addLenovoOrder(ServiceContext sc, PayAddOrderVO order, String extra) {
        try {
            return ResultDTO.getSuccessResult(payBO.addLenovoOrder(sc, order, extra));
        } catch (KairoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * 添加支付订单
     */
    public ResultDTO<AddOrderResultVO> addOrder(ServiceContext sc, PayAddOrderVO order, String extra) {
        try {
            return ResultDTO.getSuccessResult(payBO.addOrder(sc, order, extra));
        } catch (KairoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * 获取奖牌
     */
    public ResultDTO<PayLogVO> getMedal(ServiceContext sc) {
        try {
            return ResultDTO.getSuccessResult(payBO.getMedal(sc));
        } catch (RoleNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * 是否购买单机商品
     */
    public ResultDTO<String[]> isPlayerBuyOfflineGoods(ServiceContext sc, String goodsName) {
        try {
            return ResultDTO.getSuccessResult(payBO.isPlayerBuyOfflineGoods(sc, goodsName));
        } catch (NoSuchRoleException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * 支付通知
     */
    public ResultDTO<Integer> payNotify(ServiceContext sc, PayNotifyVO notify) {
        try {
            return ResultDTO.getSuccessResult(payBO.payNotify(sc, notify));
        } catch (NoSuchRoleException e) {
            e.printStackTrace();
        }
        return null;
    }
}
