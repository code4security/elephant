package com.sjhy.platform.biz.vo.proxy.pay;
/** 
 * <p>类说明:</p>
 * <p>文件名： AddTxOrderResultVO.java</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 **/
public class AddTxOrderResultVO extends AbstractResultVO{

	private String orderId;
	
	private String token;
	
	private String url_params;

	private String rmbPrice;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUrl_params() {
		return url_params;
	}

	public void setUrl_params(String url_params) {
		this.url_params = url_params;
	}

	public String getRmbPrice() {
		return rmbPrice;
	}

	public void setRmbPrice(String rmbPrice) {
		this.rmbPrice = rmbPrice;
	}
	
}
