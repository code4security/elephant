package com.sjhy.platform.client.dto.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 游戏模块开关
 * 
 * @author wcyong
 * 
 * @date 2018-12-07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModuleSwitch {
    /**
     * 模块ID
     */
    private Integer id;

    /**
     * 游戏id
     */
    private Integer gameId;

    /**
     * 渠道id
     */
    private String channelId;

    /**
     * 组件名字
     */
    private String moduleName;

    /**
     * 激活码开关
     */
    private Boolean status;
}