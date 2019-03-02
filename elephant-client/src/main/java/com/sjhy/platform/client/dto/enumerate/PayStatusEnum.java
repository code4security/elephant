package com.sjhy.platform.client.dto.enumerate;
/** 
 * <p>类说明:支付状态</p>
 * <p>文件名： PayStatusEnum.java</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 **/
public enum PayStatusEnum {
	Paying(1),//支付中
	Success(2),//成功
	SuccessAndConfirmed(3),//成功,并且客户端已经确认收到通知
	Fail(-1),//失败
	ChannelError(-2);//支付渠道通讯错误

	private int value;
	private PayStatusEnum(int value){
		this.value=value;
	}
	
	public int getValue() {
		return value;
	}
}
