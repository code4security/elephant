package com.sjhy.platform.biz.verify;

import java.util.Date;
import java.util.Map;

import com.sjhy.platform.biz.bo.VerifySessionBO;
import com.sjhy.platform.client.dto.game.GameChannelSetting;
import com.sjhy.platform.client.dto.utils.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service("1900")
public class AnzhiVerifyService implements IVerifySession{
	private static final Logger logger = Logger.getLogger( AnzhiVerifyService.class );
	
	@Override
	public String verify(String channelId, String token, Map<String, Object> extraParams) {
		// 联想参数取得
		Object gameId = extraParams.get("gameId");
		
		GameChannelSetting channelSetting = VerifySessionBO.getGameChannelSetting(gameId.toString(), channelId);
		
		if(channelSetting == null){
			logger.error("AnzhiVerifyService|method=verify|error=anzhi参数错误，gameId为空");
			
			return "";
		}
		
		Object deviceId = extraParams.get("verifyId");
		if(deviceId == null){
			logger.error("AnzhiVerifyService|method=verify|error=anzhi参数错误，deviceId为空");
			
			return "";
		}
		
		String sign = MD5Util.me().md5Hex(channelSetting.getAppKey().trim() + token + channelSetting.getAppSecret().trim());
		
		String time = UtilDate.date2Text(new Date(), "yyyyMMddHHmmssSSS");
		
		HttpUtil httpUtil = new HttpUtil();
		
		String[][] headerParams = new String[1][2];
		headerParams[0][0] = "Contend-Type";
		headerParams[0][1] = "application/x-www-form-urlencoded";
		
		// 获取接口返回结果
		String[][] postParams = new String[5][2];
		postParams[0][0] = "time";
		postParams[0][1] = time;
		
		postParams[1][0] = "appkey";
		postParams[1][1] = channelSetting.getAppKey().trim();
		
		postParams[2][0] = "cptoken";
		postParams[2][1] = token;
		
		postParams[3][0] = "sign";
		postParams[3][1] = sign;
		
		postParams[4][0] = "devideId";
		postParams[4][1] = deviceId.toString();
		
		String res = httpUtil.postRequest("https://user.anzhi.com/web/cp/checkToken", postParams);
		
		if(res != null && res.length() > 1){
			Map result = JSON.parseObject(res.toString(), Map.class);
			
			if(result != null && "1".equals(result.get("code").toString())){
				// json解析
				try{
					String data = Base64Utils.decode(result.get("data").toString());
					
					result = JSON.parseObject(data, Map.class);
					
					String uid = result.get("uid").toString();
					
					// des3解密
					return Des3Util.decrypt(uid, channelSetting.getAppSecret());
				}catch(Exception e){
					logger.error("AnzhiVerifyService|method=verify|error="+e.getMessage());
				}
			}else{
				logger.error("AnzhiVerifyService|method=verify|error="+result==null?"result null":result);
			}
		}
		
		return null;
	}

}
