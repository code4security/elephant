package com.sjhy.platform.client.deploy.enumerate;

/**
 * 邮件
 * @author Yebin
 */
public enum MailTypeEnum {
	Normal(0), // 普通邮件
	System(1), // gm系统邮件
	
	Unknown(-1), // 未知类型
	;   
	
	private int value;
	private MailTypeEnum(int value) {
		this.value = value;
	}
	public void SetMailTypeEnum(int value)
	{
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	 public static MailTypeEnum valueOf(int value) {
		 MailTypeEnum[] values = MailTypeEnum.values();
	    	for (MailTypeEnum enumValue : values) {
				if(value == enumValue.value)
					return enumValue;
			}
	    	
			return Unknown;
	    }
}