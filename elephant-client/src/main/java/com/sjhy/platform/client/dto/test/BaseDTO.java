package com.sjhy.platform.client.dto.test;

import lombok.Data;

import java.util.Date;

/**
 * @author 万二(Zheng Liu)
 */
@Data
public abstract class BaseDTO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 最后一次更新时间
     */
    private Date gmtModified;

}
