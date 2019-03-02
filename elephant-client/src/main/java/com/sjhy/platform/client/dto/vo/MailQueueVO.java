/**
 * @HJ
 */
package com.sjhy.platform.client.dto.vo;

import com.sjhy.platform.client.dto.game.Mail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yebin
 * 邮件信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailQueueVO extends Mail implements Serializable {
	private List<Long> target = new ArrayList<Long>();
	private int rewardDataId;
}