package com.sjhy.platform.biz.verify;

import com.alibaba.fastjson.JSON;
import com.sjhy.platform.biz.bo.VerifySessionBO;
import com.sjhy.platform.biz.utils.Base64Utils;
import com.sjhy.platform.biz.utils.HttpUtil;
import com.sjhy.platform.biz.utils.StringUtils;
import com.sjhy.platform.client.dto.game.GameChannelSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;

@Service("1300")
public class OppoVerifyService implements IVerifySession{
	private static final Logger logger = LoggerFactory.getLogger( OppoVerifyService.class );

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
			logger.error("OppoVerifyService|method=verify|error=OPPO验证错误，channelSetting为空");
			
			return "";
		}
		
		String consumeKey = channelSetting.getAppKey().trim();
		String appsecret  = channelSetting.getAppSecret().trim();
		
		Object ssoid = extraParams.get("verifyId");
		if(ssoid == null){
			logger.error("OppoVerifyService|method=verify|error=OPPO验证错误，ssoid为空");
			
			return "";
		}
		
		String timestamp = (int)(Calendar.getInstance().getTimeInMillis() / 1000) + "";
		
		String nonce     = new Random().nextLong() + "";
		
		String baseStr = generateBaseString(timestamp, nonce, consumeKey, token);
		String sign    = generateSign(appsecret, baseStr);
		
		String[][] headers = new String[2][2];
		
		headers[0][0] = "param";
		headers[0][1] = baseStr;
		
		headers[1][0] = "oauthSignature";
		headers[1][1] = sign;
		
		HttpUtil httpUtil = new HttpUtil();
		
		String checkUrl = "http://i.open.game.oppomobile.com/gameopen/user/fileIdInfo?fileId=_fileId_&token=_token_";
		
		try {
			checkUrl = checkUrl.replace("_fileId_", URLEncoder.encode(ssoid.toString(), "UTF-8")).replace("_token_", URLEncoder.encode(token, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
		}
		
		String res = httpUtil.getRequest(checkUrl, headers);
		if(StringUtils.isBlank(res)){
			logger.error("OppoVerifyService|method=verify|error=oppo验证错误，res为空");
			
			return "";
		}
		
		try{
			Map result = JSON.parseObject(res, Map.class);
			
			if(result != null){
				if(result.get("resultCode") != null && "200".equals(result.get("resultCode").toString())){
					logger.error("OppoVerifyService|method=verify|userName="+result.get("userName")+"|email="+result.get("email")+"|mobileNumber="+result.get("mobileNumber"));
					
					return result.get("ssoid").toString();
				}else{
					logger.error("OppoVerifyService|method=verify|error="+result.get("resultMsg"));
				}
			}
		} catch (Exception e){
			logger.error("OppoVerifyService|method=verify|error="+e.getMessage());
		}
		
		return null;
	}

	public static String generateSign(String appsecret, String baseStr){
		byte[] byteHMAC = null;
		
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			SecretKeySpec spec = null;
			String oauthSignatureKey = appsecret + "&";
			
			spec = new SecretKeySpec(oauthSignatureKey.getBytes(),"HmacSHA1");
			
			mac.init(spec);
			
			byteHMAC = mac.doFinal(baseStr.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		try {
			String sign = URLEncoder.encode(String.valueOf(Base64Utils.encode(byteHMAC)) ,"UTF-8");
			
			return sign;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static String generateBaseString(String timestamp, String nonce, String consumeKey, String token){
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(OAUTH_CONSUMER_KEY).
			append("=").
			append(URLEncoder.encode(consumeKey,"UTF-8")).
			append("&").
			append(OAUTH_TOKEN).
			append("=").
			append(URLEncoder.encode(token,"UTF-8")).
			append("&").
			append(OAUTH_SIGNATURE_METHOD).
			append("=").
			append(URLEncoder.encode(CONST_SIGNATURE_METHOD,"UTF-8")).
			append("&").
			append(OAUTH_TIMESTAMP).
			append("=").
			append(URLEncoder.encode(timestamp,"UTF-8")).
			append("&").
			append(OAUTH_NONCE).
			append("=").
			append(URLEncoder.encode(nonce,"UTF-8")).
			append("&").
			append(OAUTH_VERSION).
			append("=").
			append(URLEncoder.encode(CONST_OAUTH_VERSION,"UTF-8")).
			append("&");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		return sb.toString();
	}
}
