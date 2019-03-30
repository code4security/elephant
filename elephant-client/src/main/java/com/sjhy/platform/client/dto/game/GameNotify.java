package com.sjhy.platform.client.dto.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 游戏内展示公告信息表
 * 
 * @author HJ
 * 
 * @date 2018-12-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameNotify  implements Serializable {
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
     * 公告内容
     */
    private String content;

    /**
     * 展示状态（0,1,2）
     */
    private Integer status;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}