package com.sjhy.platform.client.dto.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 礼品码信息表
 * 
 * @author HJ
 * 
 * @date 2018-12-10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LipinmaList {
    /**
     * ID
     */
    private Integer id;

    /**
     * 游戏id
     */
    private String gameId;

    /**
     * 渠道id
     */
    private String channelId;

    /**
     * 分类（0：激活码:1：兑换码:2:通用码）
     */
    private Integer type;

    /**
     * 礼品码名称
     */
    private String lpName;

    /**
     * 礼品码说明
     */
    private String descTxt;

    /**
     * 奖励礼品id
     */
    private String lpRewardId;

    /**
     * 礼品码数量
     */
    private Integer lpNum;

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 创建时间
     */
    private Date createTime;
}