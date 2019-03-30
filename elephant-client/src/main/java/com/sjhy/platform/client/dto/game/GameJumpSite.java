package com.sjhy.platform.client.dto.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 游戏内周边按钮跳转页面信息表
 * 
 * @author HJ
 * 
 * @date 2018-12-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameJumpSite implements Serializable {
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
     * 按钮id
     */
    private Integer buttonId;

    /**
     * 1-显示 0-不显示
     */
    private Integer isShow;

    /**
     * 跳转网址
     */
    private String jumpUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 点击次数
     */
    private Integer number;

    /**
     * 点击人数
     */
    private Integer playerNum;
}