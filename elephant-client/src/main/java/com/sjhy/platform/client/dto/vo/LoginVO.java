package com.sjhy.platform.client.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录成功之后的返回值
 * @author SHACS
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO implements Serializable {
	private long playerId;
	private int serverId;
	private String versionNum;
}
