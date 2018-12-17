package com.sjhy.platform.biz.enumerate;

/**
 * 游戏平台
 * @author Administrator
 *
 */
public enum SettingEnum {
	KAIROSELF_LOGIN_VERIFY(100, "开罗自渠道验证地址"),
	
	NULL(-1,"null")
	;
	
	private int key;
	private String desc;
	
	private SettingEnum(int key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static SettingEnum valueOf(int value)  {
		SettingEnum[] values = SettingEnum.values();
		
    	for (SettingEnum enumValue : values) {
			if(value == enumValue.getKey())
				return enumValue;
		}

    	return NULL;
    }
}
