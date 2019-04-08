package com.sjhy.platform.client.dto.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 玩家购买物品信息表
 * 
 * @author HJ
 * 
 * @date 2018-12-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerPayLog implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 游戏id
     */
    private String gameId;

    /**
     * 渠道id
     */
    private String channelId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 充值金额
     */
    private BigDecimal rmb;

    /**
     * 发送虚拟货币
     */
    private Integer currencyGet;

    /**
     * 附加虚拟货币
     */
    private Integer currencyGetExtra;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 支付状态
     */
    private Integer payStatus;

    /**
     * ios返回状态值
     */
    private String iosStatus;

    /**
     * ios购买凭证
     */
    private String iosReceipt;

    /**
     * ios购买凭证验证
     */
    private String iosVerify;

    public PlayerPayLog(int id, Long aLong, String s, String s1, String s2, Date date, Float aFloat, Integer integer, Integer integer1, String s3, int payStatus, String iosStatus, String s4) {
    }
}