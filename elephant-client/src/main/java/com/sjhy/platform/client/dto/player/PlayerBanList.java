package com.sjhy.platform.client.dto.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 玩家封账号表

 * 
 * @author HJ
 * 
 * @date 2018-12-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerBanList {
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
     * 角色id
     */
    private Long roleId;

    /**
     * 封停时间
     */
    private Integer banMinute;

    /**
     * 是否处于封停状态
     */
    private Boolean isBan;

    /**
     * 封停开始时间
     */
    private Date banTime;
}