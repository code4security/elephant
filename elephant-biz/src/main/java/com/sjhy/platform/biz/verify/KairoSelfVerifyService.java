package com.sjhy.platform.biz.verify;

import java.util.Map;

import javax.annotation.Resource;

import com.sjhy.platform.biz.deploy.utils.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service("1000")
public class KairoSelfVerifyService implements IVerifySession{

	private static Logger logger = Logger.getLogger(KairoSelfVerifyService.class);
	
	@SuppressWarnings("rawtypes")
	@Override
	public String verify(String channelId, String token, Map<String, Object> extraParams) {
		
		String verifyUrl = "http://www.kairogame.cn/sdk.php/LoginNotify/login_verify";//gameSettingService.getVal(SettingEnum.KAIROSELF_LOGIN_VERIFY, 1);
		
		Object userid    = extraParams.get("verifyId");
		if(userid == null){
			logger.error("KairoSelfVerifyService|method=verify|error=开罗自渠道验证错误，userid为空");
			
			return "";
		}
		
		HttpUtil httpUtil = new HttpUtil();
		
		// 参数设定、
		String params = String.format("{\"user_id\":\"%s\", \"token\":\"%s\"}", userid.toString(), token);
		
		String res = httpUtil.postRequestEntity(verifyUrl, params);
		
		if(res == null || res.length() <= 0){
			logger.error("KairoSelfVerifyService|method=verify|error=请求地址返回错误");
			
			return "";
		}
		
		String userAccount = null;
		
		try{
			Map result = JSON.parseObject(res, Map.class);
			
			if(result != null && "1".equals(result.get("status").toString())){
				//if(result.get("user_account") != null){
				//	userAccount = result.get("user_account").toString();
				//}
				
				if(result.get("user_id") != null) {
					userAccount = result.get("user_id").toString();
				}
			}
		} catch (Exception e){
			logger.error("KairoSelfVerifyService|method=verify|error="+e.getMessage());
		}
		
		return userAccount;
	}

}
