package com.sjhy.platform.client.dto.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 玩家领取的礼品码记录信息表
 * 
 * @author HJ
 * 
 * @date 2018-12-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerGiftLog implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 礼品码id
     */
    private String giftCode;

    /**
     * 礼品码批次id
     */
    private Integer giftListId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 玩家id
     */
    private Long playerId;

    /**
     * 游戏id
     */
    private String gameId;

    /**
     * 渠道id
     */
    private String channelId;

    /**
     * 使用时间
     */
    private Date activateTime;
}