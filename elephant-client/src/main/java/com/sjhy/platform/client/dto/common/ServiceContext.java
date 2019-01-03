package com.sjhy.platform.client.dto.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Liu Zheng
 */
@Data
public class ServiceContext implements Serializable {
    // 游戏id
    private String gameId;
    // 渠道id
    private String channelId;
    // 角色id
    private Long roleId;
    // 玩家id
    private Long playerId;
    // 渠道用户id
    private Long channelUserId;
    // 服务器id
    private int serverId;
}
