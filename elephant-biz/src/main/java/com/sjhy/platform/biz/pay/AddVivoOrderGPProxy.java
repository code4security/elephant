package com.sjhy.platform.biz.pay;

import com.sjhy.platform.biz.deploy.config.GamePayConfig;
import com.sjhy.platform.client.dto.vo.pay.AddVivoOrderResultVO;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/** 
 * <p>类说明:添加支付订单</p>
 * <p>文件名： AddQihooOrderGPProxy.java</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 **/
@Service("AddVivoOrderGPProxy")
public class AddVivoOrderGPProxy extends AbstractGamePayProxy<AddVivoOrderResultVO> {

	private static Logger log = Logger.getLogger(AddVivoOrderGPProxy.class);
	
	@Override
	public String getAction() {
		return GamePayConfig.ACTION_ADD_VIVO_ORDER;
	}

	/**
	 * datas[0]-用户id
	 * datas[1]-商品id
	 * datas[2]-商品数量
	 * datas[3]-支付渠道
	 * datas[4]-备注
	 * datas[5]-操作系统类型:iso,android
	 * datas[6]-发布渠道
	 * 
	 */
	@Override
	public String getData(String[] datas) {
		long uid = Long.parseLong(datas[0]);
		
		String goodId  = datas[1];
		int goodNumber = Integer.parseInt(datas[2]);
		int channel    = Integer.parseInt(datas[3]);
		String remark  = datas[4];
				
		String publishChannel = datas[5];
		
		String roleName = datas[6];
		String channelUserId = datas[7];
		
		JSONObject requestJson = new JSONObject();
		try {
			requestJson.put("roleId", uid);
			requestJson.put("goodId", goodId);
			requestJson.put("goodNumber", goodNumber);
			requestJson.put("channel", channel);
			if (remark!=null){
				requestJson.put("remark", remark);
			}
			if (publishChannel!=null){
				requestJson.put("publishChannel", publishChannel);
			}
			
			requestJson.put("roleName", roleName);
			requestJson.put("channelUserId", channelUserId);
			
			requestJson.put("gameId", datas[8]);
			requestJson.put("serverId", datas[9]);
			requestJson.put("serverName", datas[10]);
			
			requestJson.put("notifyUrl", datas[11]);
		} catch (JSONException e) {
			log.error("支付：添加订单请求参数错误",e);
		}
		
		return requestJson.toString();
	}

	@Override
	public AddVivoOrderResultVO parseResponse(JSONObject responseJson) {
		if (responseJson == null){
			return null;
		}
		
		AddVivoOrderResultVO result = new AddVivoOrderResultVO();
		
		try {
			int resultCode = responseJson.getInt("resultCode");
			
			result.setResultCode(resultCode);
			
			if (resultCode != GamePayConfig.SUCCESS){
				return result;
			}
			
			result.setOrderId(responseJson.getString("orderId"));
			result.setAccessKey(responseJson.getString("accessKey"));
			result.setOrderNumber(responseJson.getString("orderNumber"));
			result.setOrderAmount(responseJson.getString("orderAmount"));
			
		} catch (JSONException e) {
			log.error("支付：添加订单返回参数错误",e);
		}
		
		return result;
	}
}
