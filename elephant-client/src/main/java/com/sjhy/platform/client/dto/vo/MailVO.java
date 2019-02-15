package com.sjhy.platform.client.dto.vo;

import com.sjhy.platform.client.dto.enumerate.MailTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author HJ
 * 邮件信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailVO implements Serializable {
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