package com.sjhy.platform.client.dto.base;

import com.sjhy.platform.client.srp.SRPServerSession;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LoginSessionVO implements Serializable
{
	public SRPServerSession Session = null;
	
	public long AccountId = 0;
	
	public int ServerId = 0;
	public String AccountName;
	public String Cooperate;
	
	public String CooperateId = "0";
	public String pmtChannelId = "";
	public boolean isFirstLogin = false;
	
	public int activationState = 0;// 无效激活
}
