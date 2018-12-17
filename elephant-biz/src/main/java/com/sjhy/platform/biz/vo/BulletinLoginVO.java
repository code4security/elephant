package com.sjhy.platform.biz.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 全服公告
 * @author zhangshang109
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulletinLoginVO {

	private Integer id;
	private String title;
    private String content;
    private Integer linkModelId;
    private String linkURL;
    private Integer type;
    private Date beginTime;
    private Date endTime;
}
