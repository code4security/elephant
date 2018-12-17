package com.sjhy.platform.biz.enumerate;

/**
 * 游戏平台、推广平台
 * @author Administrator
 *
 */
public enum SubChannelEnum {
	INSIDE(1, "kairo_in"),
	
	// 1~99为预留渠道Id、其他渠道从100开始
	KAIRO(100000, "kairoself"),
	YYB_WX(110001, "yyb_wx"),// 应用宝微信
	YYB_QQ(110002, "yyb_qq"),// 应用宝QQ
	TX_WX(111001, "tengxun_wx"),// 腾讯微信
	TX_QQ(111002, "tengxun_qq"),// 腾讯QQ
	QIHOO(120000, "qihoo"),// 奇虎360
	
	NULL(-1, "null")
	;
	
	private int subChannelId;
	private String subChannelName;
	
	private SubChannelEnum(int subChannelId, String subChannelName) {
		this.subChannelId = subChannelId;
		this.subChannelName = subChannelName;
	}
	
	public int getSubChannelId() {
		return subChannelId;
	}

	public void setSubChannelId(int subChannelId) {
		this.subChannelId = subChannelId;
	}

	public String getSubChannelName() {
		return subChannelName;
	}

	public void setSubChannelName(String subChannelName) {
		this.subChannelName = subChannelName;
	}

	public static SubChannelEnum valueOf(int value)  {
		SubChannelEnum[] values = SubChannelEnum.values();
    	for (SubChannelEnum enumValue : values) {
			if(value == enumValue.getSubChannelId())
				return enumValue;
		}

    	return NULL;
    }
}
