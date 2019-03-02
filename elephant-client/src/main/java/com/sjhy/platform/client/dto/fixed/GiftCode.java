package com.sjhy.platform.client.dto.fixed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 礼品码生成表
 * 
 * @author HJ
 * 
 * @date 2018-12-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftCode {
    /**
     * ID
     */
    private Integer id;

    /**
     * 礼品码信息
     */
    private String giftCode;

    /**
     * 批次号
     */
    private Integer giftListId;
}