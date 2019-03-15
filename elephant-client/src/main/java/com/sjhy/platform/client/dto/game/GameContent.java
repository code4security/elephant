package com.sjhy.platform.client.dto.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2019-03-15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameContent {
    /**
     * id
     */
    private Integer id;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 游戏id
     */
    private String gameId;

    /**
     * 渠道id
     */
    private String channelId;

    /**
     * 剩余奖牌
     */
    private Integer lastMedal;

    /**
     * 花钱得来的奖牌
     */
    private Integer rmbMedal;

    /**
     * 免费得来的奖牌
     */
    private Integer freeMedal;

    /**
     * 花费的奖牌
     */
    private Integer spendMedal;

    /**
     * 总奖牌数
     */
    private Integer totalMedal;
}