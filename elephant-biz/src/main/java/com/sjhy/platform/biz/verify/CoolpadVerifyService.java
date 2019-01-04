package com.sjhy.platform.biz.verify;

import java.util.Map;

import javax.annotation.Resource;

import com.sjhy.platform.biz.bo.VerifySessionBO;
import com.sjhy.platform.client.dto.game.GameChannelSetting;
import com.sjhy.platform.client.dto.utils.HttpUtil;
import com.sjhy.platform.client.dto.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service("2400")
public class CoolpadVerifyService implements IVerifySession{
	private static final Logger logger = Logger.getLogger( CoolpadVerifyService.class );
	
	@Override
	public String verify(String channelId, String token, Map<String, Object> extraParams) {
		// 联想参数取得
		Object gameId = extraParams.get("gameId");
		
		GameChannelSetting channelSetting = VerifySessionBO.getGameChannelSetting(gameId.toString(), channelId);
		
		if(channelSetting == null){
			logger.error("CoolpadVerifyService|method=verify|error=coolpad参数错误，gameId为空");
			
			return "";
		}
		
		String clientId     = channelSetting.getAppId().trim();
		String clientSecret = channelSetting.getAppKey().trim();
		
		String checkUrl = "https://openapi.coolyun.com/oauth2/token";
		
		HttpUtil httpUtil = new HttpUtil();
		
		String res = httpUtil.getRequest(checkUrl + "?grant_type=authorization_code"+"&client_id="+clientId+"&client_secret="+clientSecret+"&code="+token+"&redirect_uri="+clientSecret);
		
		if(StringUtils.isBlank(res)){
			logger.error("CoolpadVerifyService|method=verify|error=coolpad验证错误，res为空");
			
			return "";
		}
		
		try{
			Map result = JSON.parseObject(res, Map.class);
			
			//ResultCode=1则代表接口返回信息成功
			if(result != null && result.get("openid") != null) {
				
				return result.get("openid").toString();
			}
			
			logger.error("CoolpadVerifyService|method=verify|res="+res);
		}catch(Exception e){
			logger.error("CoolpadVerifyService|method=verify|error="+e.getMessage());
		}
		
		return null;
	}

}
