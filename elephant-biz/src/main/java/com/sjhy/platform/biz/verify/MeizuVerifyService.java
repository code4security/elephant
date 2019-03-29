package com.sjhy.platform.biz.verify;

import com.alibaba.fastjson.JSON;
import com.sjhy.platform.biz.bo.VerifySessionBO;
import com.sjhy.platform.biz.utils.HttpUtil;
import com.sjhy.platform.biz.utils.MD5Util;
import com.sjhy.platform.client.dto.game.GameChannelSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Map;

@Service("2100")
public class MeizuVerifyService implements IVerifySession{
	private static final Logger logger = LoggerFactory.getLogger( MeizuVerifyService.class );
	
	@Override
	public String verify(String channelId, String token, Map<String, Object> extraParams) {
		// 联想参数取得
		Object gameId = extraParams.get("gameId");
		
		GameChannelSetting channelSetting = VerifySessionBO.getGameChannelSetting(gameId.toString(), channelId);
		
		if(channelSetting == null){
			logger.error("MeizuVerifyService|method=verify|error=meizu参数错误，gameId为空");
			
			return "";
		}
		
		Object uid = extraParams.get("verifyId");
		if(uid == null){
			logger.error("MeizuVerifyService|method=verify|error=魅族验证错误，uid为空");
			
			return "";
		}
		
		String checkUrl = "https://api.game.meizu.com/game/security/checksession";
		
		String signType = "md5";
		
		String ts = Calendar.getInstance().getTimeInMillis() + "";
		String sign = MD5Util.me().md5Hex("app_id="+channelSetting.getAppId().trim() + "&session_id="+token+"&ts="+ts+"&uid="+uid.toString()+":"+channelSetting.getAppSecret().trim());
		
		HttpUtil httpUtil = new HttpUtil();
		
		String[][] headerParams = new String[1][2];
		headerParams[0][0] = "Contend-Type";
		headerParams[0][0] = "application/x-www-form-urlencoded";
		
		StringBuilder param = new StringBuilder();
		param.append("app_id="+channelSetting.getAppId().trim());
		param.append("&session_id="+token);
		param.append("&ts="+ts);
		param.append("&uid="+uid.toString());
		param.append("&sign_type="+signType);
		param.append("&sign="+sign);
		
		String res = httpUtil.sendHttpsPost(checkUrl + "?" +param.toString(), "");
		
		if(res == null || res.length() <= 0){
			logger.error("MeizuVerifyService|method=verify|error=魅族验证错误，res为空");
			
			return "";
		}
		
		try{
			Map result = JSON.parseObject(res, Map.class);
			
			//ResultCode=1则代表接口返回信息成功
			if(result != null && "200".equals(result.get("code").toString())) {
				return uid.toString();
			}
			
			logger.error("MeizuVerifyService|method=verify|error="+result.get("code")+"|msg="+result.get("message"));
		}catch(Exception e){
			logger.error("MeizuVerifyService|method=verify|error="+e.getMessage());
		}
		
		return null;
	}

}
