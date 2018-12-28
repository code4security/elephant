package com.sjhy.platform.client.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayLogVO {
	private List<GoodsInfo> goodsInfo = new ArrayList<GoodsInfo>();
	private Date adTime;
	private Date vipTime;

	@Data
	public static class GoodsInfo{
		private String goodsId;
		private int num;
		private Date payTime;
	}
}
