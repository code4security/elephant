package com.sjhy.platform.biz.vo.proxy.pay;
/** 
 * <p>类说明:</p>
 * <p>文件名： AddLenovoOrderResultVO.java</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 **/
public class AddLenovoOrderResultVO extends AbstractResultVO{

	private String orderId;
	
	private String channelGoodsId;
	
	private String rmbPrice;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getChannelGoodsId() {
		return channelGoodsId;
	}

	public void setChannelGoodsId(String channelGoodsId) {
		this.channelGoodsId = channelGoodsId;
	}

	public String getRmbPrice() {
		return rmbPrice;
	}

	public void setRmbPrice(String rmbPrice) {
		this.rmbPrice = rmbPrice;
	}
	
}
