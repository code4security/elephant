package com.sjhy.platform.biz.verify;

import com.sjhy.platform.biz.bo.VerifySessionBO;
import com.sjhy.platform.biz.utils.HttpUtil;
import com.sjhy.platform.biz.utils.StringUtils;
import com.sjhy.platform.biz.utils.XmlUtils;
import com.sjhy.platform.client.dto.game.GameChannelSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("1600")
public class LenovoVerifyService implements IVerifySession{
	private static final Logger logger = LoggerFactory.getLogger( LenovoVerifyService.class );

	@Override
	public String verify(String channelId, String token, Map<String, Object> extraParams) {
		// lenovo参数取得
		Object gameId = extraParams.get("gameId");
		
		GameChannelSetting channelSetting = VerifySessionBO.getGameChannelSetting(gameId.toString(), channelId);
		
		if(channelSetting == null){
			logger.error("LenovoVerifyService|method=verify|error=lenovo参数错误，gameId为空");
			
			return "";
		}
		
		String checkUrl = "http://passport.lenovo.com/interserver/authen/1.2/getaccountid?lpsust=_lpsust_&realm=_realm_";
		//checkUrl = checkUrl.replace("_lpsust_", token).replace("_realm_", "appstore.lps.lenovo.com");
		checkUrl = checkUrl.replace("_lpsust_", token).replace("_realm_", channelSetting.getAppId().trim());
		
		HttpUtil httpUtil = new HttpUtil();
		
		String res = httpUtil.getRequest(checkUrl);
		if(StringUtils.isBlank(res)){
			logger.error("LenovoVerifyService|method=verify|error=lenovo验证错误，res为空");
			
			return "";
		}
		
		// 返回参数解析
		try {
			Map<String, String> xml = XmlUtils.doParseXml(res);
			
			if(xml != null){
				if(xml.get("AccountID") != null){
					return xml.get("AccountID");
				}else{
					logger.error("LenovoVerifyService|method=verify|error=lenovo验证错误，xml解析错误_code="+xml.get("Code"));
				}
			}else{
				logger.error("LenovoVerifyService|method=verify|error=lenovo验证错误，xml解析错误");
			}
		} catch (Exception e) {
			logger.error("LenovoVerifyService|method=verify|error=lenovo验证错误，xml解析错误");
		}
		
		return null;
	}

}
