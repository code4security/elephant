package com.sjhy.platform.client.dto.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 游戏商店中展示的商品表
 * 
 * @author HJ
 * 
 * @date 2018-12-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayGoods implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 商品中文名
     */
    private String nameCn;

    /**
     * 商品英文名
     */
    private String goodsName;

    /**
     * 渠道id
     */
    private String channelId;

    /**
     * 游戏id
     */
    private String gameId;

    /**
     * 虚拟货币
     */
    private Integer currency;

    /**
     * 人民币
     */
    private Double rmb;

    /**
     * 商品说明
     */
    private String goodsDes;

    /**
     * 折扣开始时间
     */
    private Date discountBegin;

    /**
     * 折扣结束时间
     */
    private Date discountEnd;

    /**
     * 商品类型
     */
    private Integer type;
}