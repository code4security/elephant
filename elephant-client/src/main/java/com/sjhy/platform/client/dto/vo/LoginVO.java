package com.sjhy.platform.client.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录成功之后的返回值
 * @author SHACS
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO {
	private long playerId;
	private int serverId;
	private String versionNum;
}
