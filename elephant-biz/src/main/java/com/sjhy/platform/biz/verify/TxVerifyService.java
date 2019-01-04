package com.sjhy.platform.biz.verify;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sjhy.platform.biz.bo.GetBeanHelper;
import com.sjhy.platform.biz.bo.VerifySessionBO;
import com.sjhy.platform.client.dto.config.AppConfig;
import com.sjhy.platform.client.dto.enumerate.SubChannelEnum;
import com.sjhy.platform.client.dto.game.GameChannelSetting;
import com.sjhy.platform.client.dto.utils.HttpUtil;
import com.sjhy.platform.client.dto.utils.MD5Util;
import com.sjhy.platform.client.dto.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * 应用宝渠道
 * @author Administrator
 *
 */
@Service("1110")
public class TxVerifyService implements IVerifySession{
	private static final Logger logger = Logger.getLogger( TxVerifyService.class );
	
	// 手Q验证接口
	private static final String QQ_DEBUG_YSDK_CHECK_TOKEN = "http://msdktest.qq.com/auth/verify_login?timestamp=_timestamp_&appid=_appid_&sig=_sig_&openid=_openid_&encode=_encode_";
	private static final String QQ_RELEASE_YSDK_CHECK_TOKEN = "http://msdk.qq.com/auth/verify_login?timestamp=_timestamp_&appid=_appid_&sig=_sig_&openid=_openid_&encode=_encode_";
	
	// 微信验证接口
	private static final String WX_DEBUG_YSDK_CHECK_TOKEN = "http://msdktest.qq.com/auth/check_token?timestamp=_timestamp_&appid=_appid_&sig=_sig_&openid=_openid_&encode=_encode_";
	private static final String WX_RELEASE_YSDK_CHECK_TOKEN = "http://msdk.qq.com/auth/check_token?timestamp=_timestamp_&appid=_appid_&sig=_sig_&openid=_openid_&encode=_encode_";
	
	@Override
	public String verify(String channelId, String token, Map<String, Object> extraParams) {
		// sub channel信息取得
		Object subChannelId = extraParams.get("subChannelId");
		
		Object openid = extraParams.get("verifyId");
		
		if(openid == null) {
			logger.error("TxVerifyService|method=verify|error=应用宝验证错误，openid为空");
			
			return "";
		}
		
		Object gameId = extraParams.get("gameId");
		
		HttpUtil httpUtil = new HttpUtil();
		
		Map<String, String> params = new HashMap<String, String>();
		
		// 参数设定
		String checkUrl = getCheckUrl(gameId.toString(), subChannelId.toString(), openid.toString(), token, params);
		logger.error("TxVerifyService|method=verify|checkUrl="+checkUrl+"|="+JSON.toJSONString(params));
		
		String res = httpUtil.postRequestEntity(checkUrl, JSON.toJSONString(params));
		if(StringUtils.isBlank(res)){
			logger.error("TxVerifyService|method=verify|error=应用宝验证错误，res为空");
			
			return "";
		}
		
		try{
			Map<String, Object> result = JSON.parseObject(res, Map.class);
			
			if(result != null){
				if(result.get("ret") != null && "0".equals(result.get("ret").toString())){
					return openid.toString();
				}else{
					logger.error("TxVerifyService|method=verify|error="+result.get("msg")+"|token="+token);
				}
			}
		} catch (Exception e){
			logger.error("TxVerifyService|method=verify|error="+e.getMessage()+"|token="+token);
		}
		
		return null;
	}

	/**
	 * 取得验证url
	 * @param subChannelId
	 * @return
	 */
	private String getCheckUrl(String gameId, String subChannelId, String openid, String openkey, Map<String, String> params) {
		// 请求地址
		String checkUrl = "";
		
		// timestamp
		Date timestamp  = new Date();
		
		// 参数获得
		GameChannelSetting channelSetting = VerifySessionBO.getGameChannelSetting(gameId, subChannelId);
		
		if(channelSetting == null){
			logger.error("TxVerifyService|method=verify|error=应用宝验证参数错误，channelSetting为空|subChannelId=" + subChannelId);
			
			return "";
		}
		
		// 生产环境开关取得
		String debug = GetBeanHelper.getServerConfig().getProperty(AppConfig.SERVER_DEBUG, "false");
		if(subChannelId.equals(""+SubChannelEnum.TX_QQ.getSubChannelId())){
			
			String appkey = channelSetting.getPublicKey();
			
			if(Boolean.valueOf(debug).booleanValue()) {
				checkUrl = QQ_DEBUG_YSDK_CHECK_TOKEN;
			}else{
				checkUrl = QQ_RELEASE_YSDK_CHECK_TOKEN;
			}
			
			// 签名算出
			String sign  = MD5Util.me().md5Hex(appkey + timestamp.getTime() / 1000);
			
			checkUrl = checkUrl.replace("_timestamp_", timestamp.getTime()/1000+"").replace("_appid_", channelSetting.getAppId()).replace("_sig_", sign).replace("_openid_", openid).replace("_openkey_", openkey);
			
			params.put("appid", channelSetting.getAppId());
			params.put("openid", openid);
			params.put("openkey", openkey);
			//params.put("userip", channelSetting.getAppId());
		}else if(subChannelId.equals(""+SubChannelEnum.TX_WX.getSubChannelId())){
			
			String appkey = channelSetting.getPublicKey();
			
			if(Boolean.valueOf(debug).booleanValue()) {
				checkUrl = WX_DEBUG_YSDK_CHECK_TOKEN;
			}else{
				checkUrl = WX_RELEASE_YSDK_CHECK_TOKEN;
			}
			
			// 签名算出
			String sign  = MD5Util.me().md5Hex(appkey + timestamp.getTime() / 1000);
						
			checkUrl = checkUrl.replace("_timestamp_", timestamp.getTime()/1000+"").replace("_appid_", channelSetting.getAppId()).replace("_sig_", sign).replace("_openid_", openid).replace("_openkey_", openkey);
			
			params.put("openid", openid);
			params.put("accessToken", openkey);
		}

		//params[0][0] = "userip";
		//params[0][1] = channelSetting.getAppId();
		
		return checkUrl.replace("_encode_", "2");
	}
}
