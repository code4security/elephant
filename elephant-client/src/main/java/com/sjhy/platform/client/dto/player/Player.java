package com.sjhy.platform.client.dto.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 玩家信息表
 * 
 * @author HJ
 * 
 * @date 2018-12-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    /**
     * ID
     */
    private Integer id;

    /**
     * 玩家id
     */
    private Long playerId;

    /**
     * 游戏id
     */
    private String gameId;

    /**
     * 渠道id
     */
    private String channelId;

    /**
     * 服务器id
     */
    private Integer serverId;

    /**
     * 设备
     */
    private String deviceModel;

    /**
     * 设备id
     */
    private String deviceUniquelyId;

    /**
     * 首次登陆
     */
    private Date firstLoginTime;

    /**
     * 首次登陆ip
     */
    private String firstLoginIp;

    /**
     * 最后登陆时间
     */
    private Date lastLoginTime;

    /**
     * 最后登陆ip
     */
    private String lastLoginIp;

    /**
     * ip所在区域
     */
    private Integer ipRegion;
}