package com.sjhy.platform.biz.verify;

import java.util.Map;

import javax.annotation.Resource;

import com.sjhy.platform.biz.bo.VerifySessionBO;
import com.sjhy.platform.client.dto.game.GameChannelSetting;
import com.sjhy.platform.biz.deploy.utils.HttpUtil;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service("3500")
public class HuluxiaVerifyService implements IVerifySession{
	private static final Logger logger = LoggerFactory.getLogger( HuluxiaVerifyService.class );
	
	private static final String url = "http://api.user.huluxia.com/account/token/verify/2.0";
	
	@Override
	public String verify(String channelId, String token, Map<String, Object> extraParams) {
		// 联想参数取得
		Object gameId = extraParams.get("gameId");
		
		GameChannelSetting channelSetting = VerifySessionBO.getGameChannelSetting(gameId.toString(), channelId);
		
		if(channelSetting == null){
			logger.error("HuluxiaVerifyService|method=verify|error=huluxia参数错误，gameId为空");
			
			return "";
		}
		
		Object verifyId = extraParams.get("verifyId");
		if(verifyId == null){
			logger.error("HuluxiaVerifyService|method=verify|error=huluxia参数错误，verifyId为空");
			
			return "";
		}
		
		String[] data = verifyId.toString().split("\\^");
		
		String deviceCode = data[0];
		String uid        = data[1];
		
		HttpUtil httpUtil = new HttpUtil();
		
		String[][] params = new String[4][2];
		params[0][0] = "apk_id";
		params[0][1] = channelSetting.getAppId();
		
		params[1][0] = "token";
		params[1][1] = token;
		
		params[2][0] = "device_code";
		params[2][1] = deviceCode;
		
		params[3][0] = "uid";
		params[3][1] = uid;
		
		// 获取接口返回结果
		String res = httpUtil.postRequest(url, params);
		if(res != null && res.length() > 1){
			try{
				Map<String, String> result = JSON.parseObject(res, Map.class);
				
				String status = String.valueOf(result.get("status"));
				
				if("1".equals(status)){
					// json解析
					return uid;
				}
			}catch(Exception e){
				logger.error("HuluxiaVerifyService|method=verify|error="+e.getMessage());
			}
		}else{
			logger.error("HuluxiaVerifyService|method=verify|error=验证token错误|token="+token+"|uid="+uid+"|device_code="+deviceCode);
		}
		
		return null;
	}

}
