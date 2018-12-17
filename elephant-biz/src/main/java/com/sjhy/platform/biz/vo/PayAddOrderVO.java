package com.sjhy.platform.biz.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>类说明:</p>
 * <p>文件名： PayAddOrderVO.java</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayAddOrderVO {
	private String goodId;
	private int goodNumber;
	private int channel;
	private String remark;
	private String publishChannel;
	
	private String pf;
	private String pfkey;
	private String openId;
	private String openKey;
}
