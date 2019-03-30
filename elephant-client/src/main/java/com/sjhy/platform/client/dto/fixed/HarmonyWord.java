package com.sjhy.platform.client.dto.fixed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
public class HarmonyWord implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 违禁词名称
     */
    private String name;
}