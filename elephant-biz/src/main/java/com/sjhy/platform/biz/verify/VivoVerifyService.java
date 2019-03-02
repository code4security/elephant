package com.sjhy.platform.biz.verify;

import java.util.Map;

import com.sjhy.platform.biz.bo.VerifySessionBO;
import com.sjhy.platform.client.dto.game.GameChannelSetting;
import com.sjhy.platform.biz.deploy.utils.HttpUtil;
import com.sjhy.platform.biz.deploy.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
@Service("2000")
public class VivoVerifyService implements IVerifySession{
	private static final Logger logger = Logger.getLogger( VivoVerifyService.class );
	
	@Override
	public String verify(String channelId, String token, Map<String, Object> extraParams) {
		// 联想参数取得
		Object gameId = extraParams.get("gameId");
		
		GameChannelSetting channelSetting = VerifySessionBO.getGameChannelSetting(gameId.toString(), channelId);
		
		if(channelSetting == null){
			logger.error("VivoVerifyService|method=verify|error=baidu参数错误，gameId为空");
			
			return "";
		}
		
		String checkUrl = "https://usrsys.vivo.com.cn/sdk/user/auth.do?";
		
		HttpUtil httpUtil = new HttpUtil();
		
		String res = httpUtil.sendHttpsPost(checkUrl+"authtoken="+token+"&from="+gameId.toString(), "");
		if(StringUtils.isBlank(res)){
			logger.error("VivoVerifyService|method=verify|error=vivo验证错误，res为空");
			
			return "";
		}
		
		try{
			Map result = JSON.parseObject(res, Map.class);
			
			if(result != null){
				if(result.get("retcode") != null && "0".equals(result.get("retcode").toString())){
					logger.error("VivoVerifyService|method=verify|验证Ok");
					
					if(result.get("data") != null){
						return ((Map)result.get("data")).get("openid").toString();
					}
				}else{
					logger.error("VivoVerifyService|method=verify|error="+result.get("retcode"));
				}
			}
		} catch (Exception e){
			logger.error("VivoVerifyService|method=verify|error="+e.getMessage());
		}
		
		return null;
	}

}
