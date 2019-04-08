<<<<<<< HEAD
package com.sjhy.platform.client.dto.vo.pay;

import java.io.Serializable;

/**
 * <p>类说明:</p>
 * <p>文件名： AddOrderResultVO.java</p>
 * <p>创建人及时间：	宋士龙 2012-11-29</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 **/
public class AddOrderResultVO extends AbstractResultVO implements Serializable {

	private String orderId;
	private String sign;
	private String goodId;
	private String goodName;
	private String goodDic;

	private String rmbPrice;
	private String ingot;
	
	public String getIngot() {
		return ingot;
	}

	public void setIngot(String ingot) {
		this.ingot = ingot;
	}

	private long createTime;
	
	private String notifyUrl;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getGoodDic() {
		return goodDic;
	}

	public void setGoodDic(String goodDic) {
		this.goodDic = goodDic;
	}

	public String getRmbPrice() {
		return rmbPrice;
	}

	public void setRmbPrice(String rmbPrice) {
		this.rmbPrice = rmbPrice;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
}
=======
package com.sjhy.platform.client.dto.vo.pay;

import java.io.Serializable;

/**
 * <p>类说明:</p>
 * <p>文件名： AddOrderResultVO.java</p>
 * <p>创建人及时间：	宋士龙 2012-11-29</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 **/
public class AddOrderResultVO extends AbstractResultVO implements Serializable {

	private String orderId;
	private String sign;
	private String goodId;
	private String goodName;
	private String goodDic;

	private String rmbPrice;
	private String ingot;
	
	public String getIngot() {
		return ingot;
	}

	public void setIngot(String ingot) {
		this.ingot = ingot;
	}

	private long createTime;
	
	private String notifyUrl;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getGoodDic() {
		return goodDic;
	}

	public void setGoodDic(String goodDic) {
		this.goodDic = goodDic;
	}

	public String getRmbPrice() {
		return rmbPrice;
	}

	public void setRmbPrice(String rmbPrice) {
		this.rmbPrice = rmbPrice;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
}
>>>>>>> fbb421ed9b7b48b40b170e3fe923dd58907356d6
