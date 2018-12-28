package com.sjhy.platform.client.dto.vo;

import com.sjhy.platform.client.dto.srp.SRPServerSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginSessionVO implements Serializable
{
	public SRPServerSession Session = null;
	
	public long playerId = 0;
	public String deviceUniquelyId;
	public int ServerId = 0;
	public String channelUserId = "0";
	public String channelId = "";
	public boolean isFirstLogin = false;
	
	public int activationState = 0;// 无效激活
}
