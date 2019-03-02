package com.sjhy.platform.client.dto.enumerate;
/** 
 * <p>类说明:操作类型枚举：1-金币；2-元宝</p>
 * <p>文件名： CurrencyTypeEnum.java</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 **/
public enum CurrencyTypeEnum {
	Gold((short)1),   // 金币
	Money((short)2),  // 元宝
	;
	
	private short value;
	private CurrencyTypeEnum(short value){
		this.value=value;
	}
	
	public short getValue() {
		return value;
	}
}
