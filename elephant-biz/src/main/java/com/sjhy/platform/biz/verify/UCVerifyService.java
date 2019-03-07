package com.sjhy.platform.biz.verify;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.sjhy.platform.biz.bo.VerifySessionBO;
import com.sjhy.platform.client.dto.game.GameChannelSetting;
import com.sjhy.platform.biz.deploy.utils.HttpUtil;
import com.sjhy.platform.biz.deploy.utils.MD5Util;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service("1700")
public class UCVerifyService implements IVerifySession{

	private static Logger logger = LoggerFactory.getLogger(UCVerifyService.class);
	
	@SuppressWarnings("rawtypes")
	@Override
	public String verify(String channelId, String token, Map<String, Object> extraParams) {
		
		String verifyUrl = "http://sgsdkaccount.9game.cn/sg/cp/sgaccount.verifySession";//gameSettingService.getVal(SettingEnum.KAIROSELF_LOGIN_VERIFY, 1);
		
		//Object sid    = extraParams.get("verifyId");
		Object sid    = token;
		if(sid == null){
			logger.error("UCVerifyService|method=verify|error=UC渠道验证错误，sid为空");
			
			return "";
		}
		
		// UC参数取得
		Object gameId = extraParams.get("gameId");
		
		GameChannelSetting channelSetting = VerifySessionBO.getGameChannelSetting(gameId.toString(), channelId);
		
		if(channelSetting == null){
			logger.error("UCVerifyService|method=verify|error=UC参数错误，gameId为空");
			
			return "";
		}
		
		String sign = createSign(sid.toString(), channelSetting.getAppKey());
		
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("id", Calendar.getInstance().getTimeInMillis());
		
		Map<String, String> data = new HashMap<String, String>();
		data.put("sid", sid.toString());
		json.put("data", data);
		
		Map<String, String> game = new HashMap<String, String>();
		game.put("gameId", channelSetting.getAppId());
		
		json.put("game", game);
		json.put("sign", sign);
		
		HttpUtil httpUtil = new HttpUtil();
		
		// 参数设定、
		String params = JSON.toJSONString(json);
		
		String res    = httpUtil.postRequestEntity(verifyUrl, params);
		
		if(res == null || res.length() <= 0){
			logger.error("UCVerifyService|method=verify|error=请求地址返回错误|sid="+sid);
			
			return "";
		}
		
		logger.error("UCVerifyService|method=verify|sdk返回信息|res="+res);
		
		String userAccount = null;
		
		try{
			JSONObject result = JSON.parseObject(res);
			
			if(result != null && result.get("state") != null){
				Map status = (Map)result.get("state");
				
				if(status.get("code") != null && "1".equals(status.get("code").toString())) {
					Map dataResult = (Map)result.get("data");
					
					if(dataResult.get("accountId") != null) {
						userAccount    = dataResult.get("accountId").toString();
					}
				}
			}
		} catch (Exception e){
			logger.error("UCVerifyService|method=verify|error="+e.getMessage());
		}
		
		return userAccount;
	}
	
	private String createSign(String sid, String appkey) {
		String sign = "sid=" + sid + appkey;
		
		return MD5Util.me().md5Hex(sign);
	}
}
