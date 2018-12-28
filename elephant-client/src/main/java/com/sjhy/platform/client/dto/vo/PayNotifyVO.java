package com.sjhy.platform.client.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>类说明:</p>
 * <p>文件名： PayNotifyVO.java</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayNotifyVO {

	private String orderId;

	private int payStatus;
	private int virtualAmount;
	private int amountUnit;

	private String channelId;// 渠道Id
	private String goodsName;// 商品注册Id
	private float rmb;
}
