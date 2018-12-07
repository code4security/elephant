package com.sjhy.platform.client.dto.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 玩家封账号表

 * 
 * @author wcyong
 * 
 * @date 2018-12-07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerBanList {
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