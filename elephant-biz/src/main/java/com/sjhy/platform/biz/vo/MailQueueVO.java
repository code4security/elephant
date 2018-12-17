/**
 * @HJ
 */
package com.sjhy.platform.biz.vo;

import com.sjhy.platform.client.dto.game.Mail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yebin
 * 邮件信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailQueueVO extends Mail {
	private List<Long> target = new ArrayList<Long>();
	private int rewardDataId;
}