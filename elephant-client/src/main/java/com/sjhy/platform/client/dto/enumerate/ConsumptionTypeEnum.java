package com.sjhy.platform.client.dto.enumerate;
/** 
 * <p>类说明:消费点枚举
 * <p>文件名： ConsumptionTypeEnum.java</p>
 * <p>创建人及时间：	</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 **/
public enum ConsumptionTypeEnum {
	Unknown(0),// 未知
	;
	
	private short value;
	private ConsumptionTypeEnum(int value){
		this.value=(short)value;
	}
	
	public short getValue() {
		return value;
	}
}
