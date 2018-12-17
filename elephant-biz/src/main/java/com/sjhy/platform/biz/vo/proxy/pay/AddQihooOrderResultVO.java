package com.sjhy.platform.biz.vo.proxy.pay;
/** 
 * <p>类说明:</p>
 * <p>文件名： AddQihooOrderResultVO.java</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 **/
public class AddQihooOrderResultVO extends AbstractResultVO{

	private String orderId;
	
	private String token_id;
	
	private String order_token;

	private String rmbPrice;
	
	private String userId;
	
	private String notifyUrl;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getToken_id() {
		return token_id;
	}

	public void setToken_id(String token_id) {
		this.token_id = token_id;
	}

	public String getOrder_token() {
		return order_token;
	}

	public void setOrder_token(String order_token) {
		this.order_token = order_token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRmbPrice() {
		return rmbPrice;
	}

	public void setRmbPrice(String rmbPrice) {
		this.rmbPrice = rmbPrice;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
}
