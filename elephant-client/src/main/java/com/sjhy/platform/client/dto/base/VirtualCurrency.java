package com.sjhy.platform.client.dto.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 游戏内虚拟货币单位信息表
 * 
 * @author HJ
 * 
 * @date 2018-12-06
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
     * 货币单位
     */
    private String unit;

}