package com.sjhy.platform.client.dto.fixed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 游戏内虚拟货币单位信息表
 * 
 * @author HJ
 * 
 * @date 2018-12-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VirtualCurrency {
    /**
     * ID
     */
    private Integer id;

    /**
     * 货币编号
     */
    private String unit;

    /**
     * 货币说明
     */
    private String explain;
}