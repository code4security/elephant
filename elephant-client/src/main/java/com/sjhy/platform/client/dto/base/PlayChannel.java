package com.sjhy.platform.client.dto.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 渠道玩家信息表
 * 
 * @author HJ
 * 
 * @date 2018-12-06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayChannel {
    /**
     * ID
     */
    private Integer id;

    /**
     * 渠道id
     */
    private String channelId;

    /**
     * 子渠道
     */
    private Integer subChannel;

    /**
     * 渠道用户id
     */
    private String channelUserId;

    /**
     * 玩家id
     */
    private Long playerId;

}