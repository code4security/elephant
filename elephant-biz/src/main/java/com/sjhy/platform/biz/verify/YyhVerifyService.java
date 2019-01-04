package com.sjhy.platform.biz.verify;

import java.util.Map;

import javax.annotation.Resource;

import com.sjhy.platform.biz.bo.VerifySessionBO;
import com.sjhy.platform.client.dto.game.GameChannelSetting;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("2200")
public class YyhVerifyService implements IVerifySession{
	private static final Logger logger = Logger.getLogger( YyhVerifyService.class );
	
	@Override
	public String verify(String channelId, String token, Map<String, Object> extraParams) {
		// 联想参数取得
		Object gameId = extraParams.get("gameId");
		
		GameChannelSetting channelSetting = VerifySessionBO.getGameChannelSetting(gameId.toString(), channelId);
		
		if(channelSetting == null){
			logger.error("YyhVerifyService|method=verify|error=应用汇参数错误，gameId为空");
			
			return "";
		}
		
		return null;
	}

	
}
