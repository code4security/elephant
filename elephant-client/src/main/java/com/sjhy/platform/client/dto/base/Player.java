package com.sjhy.platform.client.dto.base;

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
 * @date 2018-12-06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    /**
     * ID
     */
    private Long playerId;

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
     * 渠道中文名
     */
    private String channelName;

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