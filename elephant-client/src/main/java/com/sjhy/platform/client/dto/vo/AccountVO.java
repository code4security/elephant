package com.sjhy.platform.client.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long playerId;
	private String deviceUniquelyId;
	private String deviceUniquelyCode;
	private String password;
	private Integer serverId;
	private Integer activationState = 0;// 默认不需要激活码
	private String chUserId;
	private String channelId;
	private int gameId;

}
