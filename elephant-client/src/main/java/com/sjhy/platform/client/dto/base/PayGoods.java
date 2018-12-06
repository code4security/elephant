package com.sjhy.platform.client.dto.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 游戏商店中展示的商品表
 * 
 * @author HJ
 * 
 * @date 2018-12-06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayGoods {
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
    private Integer gameId;

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

}