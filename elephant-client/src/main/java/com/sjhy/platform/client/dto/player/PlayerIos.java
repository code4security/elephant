package com.sjhy.platform.client.dto.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2019-03-06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerIos {
    /**
     * ios的id
     */
    private Long iosId;

    /**
     * 游戏id
     */
    private String gameId;

    /**
     * 渠道id
     */
    private String channelId;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 月卡创建时间
     */
    private Date monthlyCard;

    /**
     * 月卡到期时间，为null永久
     */
    private Date endTime;
}