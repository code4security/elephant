package com.sjhy.platform.client.dto.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 游戏名单信息表
 * 
 * @author HJ
 * 
 * @date 2018-12-06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    /**
     * ID
     */
    private Integer id;

    /**
     * 中文名
     */
    private String name;

    /**
     * 英文名
     */
    private String nameEn;

    /**
     * 类型（单机、网游）
     */
    private Integer type;

}