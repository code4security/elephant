package com.sjhy.platform.client.dto.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 登陆服务器历史记录表
 * 
 * @author HJ
 * 
 * @date 2018-12-06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServerHistory {
    /**
     * 玩家id
     */
    private Long playerId;

    /**
     * 服务器id
     */
    private Integer serverId;

    /**
     * 最后登陆时间
     */
    private Date lastLoginTime;

}