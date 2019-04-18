package com.sjhy.platform.client.dto.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2019-04-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameConfig {
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
     * 单位时间
     */
    private Long untiTime;

    /**
     * 最大字节
     */
    private Long maxByte;

}