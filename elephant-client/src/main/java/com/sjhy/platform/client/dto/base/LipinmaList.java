package com.sjhy.platform.client.dto.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 礼品码生成表
 * 
 * @author HJ
 * 
 * @date 2018-12-06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LipinmaList {
    /**
     * ID
     */
    private Integer id;

    /**
     * 礼品码信息
     */
    private String lpListId;

    /**
     * 批次号
     */
    private Integer lpId;

}