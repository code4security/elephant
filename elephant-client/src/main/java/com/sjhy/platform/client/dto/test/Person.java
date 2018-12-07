package com.sjhy.platform.client.dto.test;

import com.sjhy.platform.client.dto.base.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 万二(Zheng Liu)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person extends BaseDTO {

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

}
