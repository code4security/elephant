package com.sjhy.platform.biz.vo.proxy.pay;
/** 
 * <p>类说明:</p>
 * <p>文件名： AddJinliOrderResultVO.java</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 **/
public class AddJinliOrderResultVO extends AbstractResultVO{

	private String orderId;
	private String orderNo;
	
	private String apiKey;
	
	private String outOrderNo;

	private String submitTime;
	
	private String orderInfo;
	
	private String rmbPrice;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getOutOrderNo() {
		return outOrderNo;
	}

	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getRmbPrice() {
		return rmbPrice;
	}

	public void setRmbPrice(String rmbPrice) {
		this.rmbPrice = rmbPrice;
	}
	
}
