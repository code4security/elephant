package com.sjhy.platform.biz.verify;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.sjhy.platform.biz.bo.VerifySessionBO;
import com.sjhy.platform.client.dto.game.GameChannelSetting;
import com.sjhy.platform.biz.deploy.utils.HashKit;
import com.sjhy.platform.biz.deploy.utils.HttpUtil;
import com.sjhy.platform.biz.deploy.utils.StringUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service("1400")
public class MiVerifyService implements IVerifySession{
	private static final Logger logger = LoggerFactory.getLogger( MiVerifyService.class );

	public static final String OAUTH_CONSUMER_KEY = "oauthConsumerKey";
	public static final String OAUTH_TOKEN = "oauthToken";
	public static final String OAUTH_SIGNATURE_METHOD = "oauthSignatureMethod";
	public static final String OAUTH_SIGNATURE = "oauthSignature";
	public static final String OAUTH_TIMESTAMP = "oauthTimestamp";
	public static final String OAUTH_NONCE = "oauthNonce";
	public static final String OAUTH_VERSION = "oauthVersion";
	public static final String CONST_SIGNATURE_METHOD = "HMAC-SHA1";
	public static final String CONST_OAUTH_VERSION = "1.0";
	
	@Override
	public String verify(String channelId, String token, Map<String, Object> extraParams) {
		// oppo参数取得
		Object gameId = extraParams.get("gameId");
		
		GameChannelSetting channelSetting = VerifySessionBO.getGameChannelSetting(gameId.toString(), channelId);
		
		if(channelSetting == null){
			logger.error("MiVerifyService|method=verify|error=小米参数错误，channelSetting为空");
			
			return "";
		}
		
		String consumeKey = channelSetting.getAppId().trim();
		String appsecret  = channelSetting.getAppSecret().trim();
		
		Object uid = extraParams.get("verifyId");
		if(uid == null){
			logger.error("MiVerifyService|method=verify|error=小米参数错误，uid为空");
			
			return "";
		}
		
		Map<String, String> packageParams = new HashMap<String, String>();
		packageParams.put("appId", consumeKey);
		packageParams.put("session", token);
		packageParams.put("uid", uid.toString());
		
		String signatureBaseStr = HashKit.createSignByKey(packageParams);
		
		String signature = "";
		try {
			signature = HashKit.HmacSHA1Encrypt(signatureBaseStr, appsecret);
		} catch (Exception e1) {
		}
		
		String paramstr = "";
		try {
			paramstr = signatureBaseStr.replace(token, URLEncoder.encode(token, "UTF-8")) + "&signature=" + signature;
		} catch (UnsupportedEncodingException e1) {
			logger.error("MiVerifyService|method=verify|error=mi验证错误，paramstr为空");
			
			return "";
		}
		
		HttpUtil httpUtil = new HttpUtil();
		
		String checkUrl = "http://mis.migc.xiaomi.com/api/biz/service/verifySession.do?" + paramstr;
				
		String res = httpUtil.getRequest(checkUrl);
		if(StringUtils.isBlank(res)){
			logger.error("MiVerifyService|method=verify|error=mi验证错误，res为空");
			
			return "";
		}
		
		try{
			Map result = JSON.parseObject(res, Map.class);
			
			if(result != null){
				if(result.get("errcode") != null && "200".equals(result.get("errcode").toString())){
					logger.error("MiVerifyService|method=verify|验证Ok");
					
					return uid.toString();
				}else{
					logger.error("MiVerifyService|method=verify|error="+result.get("errMsg"));
				}
			}
		} catch (Exception e){
			logger.error("MiVerifyService|method=verify|error="+e.getMessage());
		}
		
		return null;
	}

}
