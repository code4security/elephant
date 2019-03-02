package com.sjhy.platform.client.dto.enumerate;
/** 
 * <p>类说明:支付渠道</p>
 * <p>文件名： PayChannelEnum.java</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 **/
public enum PayChannelEnum {
	ShijunPay(1),//机锋
	AliPay(2),   //支付宝
	WxPay(3),    //微信
	;
	
	private int value;
	
	private PayChannelEnum(int value){
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static PayChannelEnum valueOf(int value){
		PayChannelEnum[] values = PayChannelEnum.values();
		
    	for (PayChannelEnum enumValue : values) {
			if(value == enumValue.getValue())
				return enumValue;
		}

    	return null;
	}
}
