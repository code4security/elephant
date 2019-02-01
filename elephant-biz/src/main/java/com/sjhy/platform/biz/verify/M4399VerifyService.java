package com.sjhy.platform.biz.verify;

import java.util.Map;

import com.sjhy.platform.biz.bo.VerifySessionBO;
import com.sjhy.platform.client.dto.game.GameChannelSetting;
import com.sjhy.platform.biz.deploy.utils.HttpUtil;
import com.sjhy.platform.biz.deploy.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service("2600")
public class M4399VerifyService implements IVerifySession{
	private static final Logger logger = Logger.getLogger( M4399VerifyService.class );
	
	@Override
	public String verify(String channelId, String token, Map<String, Object> extraParams) {
		// 联想参数取得
		Object gameId = extraParams.get("gameId");
		
		GameChannelSetting channelSetting = VerifySessionBO.getGameChannelSetting(gameId.toString(), channelId);
		
		if(channelSetting == null){
			logger.error("M4399VerifyService|method=verify|error=m4399参数错误，gameId为空");
			
			return "";
		}
		
		Object uid = extraParams.get("verifyId");
		if(uid == null){
			logger.error("M4399VerifyService|method=verify|error=m4399验证错误，uid为空");
			
			return "";
		}
		
		String checkUrl = "http://m.4399api.com/openapi/oauth-check.html?" + "state="+token+"&uid="+uid;
		
		HttpUtil httpUtil = new HttpUtil();
		
		String res = httpUtil.getRequest(checkUrl);
		if(StringUtils.isBlank(res)){
			logger.error("M4399VerifyService|method=verify|error=m4399验证错误，res为空");
			
			return "";
		}
		
		try{
			Map result = JSON.parseObject(res, Map.class);
			
			if(result != null && "100".equals(result.get("code").toString())){
				
				result = JSON.parseObject(result.get("result").toString(), Map.class);
				
				return result.get("uid").toString();
			}else{
				logger.error("M4399VerifyService|method=verify|error="+result.get("code")+"|msg="+result.get("message"));
			}
		}catch(Exception e){
			logger.error("M4399VerifyService|method=verify|error="+e.getMessage());
		}
		
		return null;
	}

}
