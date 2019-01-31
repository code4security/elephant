package com.sjhy.platform.biz.pay;

import com.sjhy.platform.client.dto.config.GamePayConfig;
import com.sjhy.platform.client.dto.vo.pay.AddOrderResultVO;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/** 
 * <p>类说明:添加支付订单</p>
 * <p>文件名： AddOrderGPProxy.java</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 **/
@Service("AddOrderGPProxy")
public class AddOrderGPProxy extends AbstractGamePayProxy<AddOrderResultVO> {

	private static Logger logger = Logger.getLogger(AddOrderGPProxy.class);
	
	@Override
	public String getAction() {
		return GamePayConfig.ACTION_ADD_ORDER;
	}

	/**
	 * make Json str
	 * 
	 */
	@Override
	public String getData(String[] datas) {
		JSONObject requestJson = new JSONObject();
		
		try {
			// 角色ID
			requestJson.put("roleId", datas[0]);
			requestJson.put("roleName", datas[1]);
			// 游戏ID
			requestJson.put("gameId", datas[2]);
			// server_id
			requestJson.put("serverId", datas[3]);
			// server_name(ip&端口)
			requestJson.put("serverName", datas[4]);
			// 商品ID
			requestJson.put("goodId", datas[5]);
			// 商品数量
			requestJson.put("goodNumber", datas[6]);
			// 支付方式
			requestJson.put("payChannel", datas[7]);
			
			if (datas[8] != null){
				requestJson.put("remark", datas[8]);
			}
			// 操作系统
			requestJson.put("osType", datas[9]);
			if (datas[9] != null){
				requestJson.put("publishChannel", datas[10]);
			}
			
			requestJson.put("realChUserId", datas[11]);
		} catch (JSONException e) {
			logger.error("支付：添加订单请求参数错误",e);
		}
		
		return requestJson.toString();
	}

	@Override
	public AddOrderResultVO parseResponse(JSONObject responseJson) {
		if (responseJson == null){
			return null;
		}
		
		AddOrderResultVO result = new AddOrderResultVO();
		
		try {
			int resultCode = responseJson.getInt("resultCode");
			result.setResultCode(resultCode);
			
			if (resultCode != GamePayConfig.SUCCESS) {
				return result;
			}
			
			result.setOrderId(responseJson.getString("orderId"));
		} catch (JSONException e) {
			logger.error("支付：添加订单返回参数错误",e);
		}
		
		return result;
	}
}
