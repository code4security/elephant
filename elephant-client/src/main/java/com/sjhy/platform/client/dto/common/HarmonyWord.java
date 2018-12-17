package com.sjhy.platform.client.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 违禁词表
 * 
 * @author HJ
 * 
 * @date 2018-12-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HarmonyWord {
    /**
     * ID
     */
    private Integer id;

    /**
     * 违禁词名称
     */
    private String name;
}