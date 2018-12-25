/**
 * @HJ
 */
package com.sjhy.platform.biz.vo;

import com.sjhy.platform.biz.enumerate.MailTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Yebin
 * 邮件信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailVO {
	private long mailId;
	private int senderId;
	private MailTypeEnum mailType;
	private String title;
	private String context;
	private Date sendTime;
	private int goodId = -1;
	private int goodNum = -1;
	private boolean status;
	private String admiralAvatar;
}