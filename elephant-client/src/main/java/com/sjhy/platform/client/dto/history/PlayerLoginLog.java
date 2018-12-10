package com.sjhy.platform.client.dto.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 玩家登陆日志信息表
 * 
 * @author HJ
 * 
 * @date 2018-12-10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerLoginLog {
    /**
     * ID
     */
    private Integer id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 玩家id
     */
    private Long playerId;

    /**
     * 是否登陆
     */
    private Boolean isLogin;

    /**
     * 服务器id
     */
    private Integer serverId;

    /**
     * 游戏id
     */
    private String gameId;

    /**
     * 渠道id
     */
    private String channelId;

    /**
     * 设备名称
     */
    private String deviceModel;

    /**
     * 设备id
     */
    private String deviceUniquelyId;
}