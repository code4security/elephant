package com.sjhy.platform.biz.verify;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.sjhy.platform.biz.bo.VerifySessionBO;
import com.sjhy.platform.client.dto.game.GameChannelSetting;
import com.sjhy.platform.client.dto.utils.Base64Utils;
import com.sjhy.platform.client.dto.utils.HttpUtil;
import com.sjhy.platform.client.dto.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service("1800")
public class JinliVerifyService implements IVerifySession{
	private static final Logger logger = Logger.getLogger( JinliVerifyService.class );
	
	private static String port = "443";
	private static String verify_url = "https://id.gionee.com:"+port+"/account/verify.do";
//	private static String apiKey = "7EBF116B7DC847C4A109F51C858320E4";     //替换成商户申请获取的APIKey
//	private static String secretKey = "1F2DF0D4022B48DEA3E18537E4159DF3";  //替换成商户申请获取的SecretKey
	private static String host = "id.gionee.com";
	private static String url = "/account/verify.do";
	private static String method = "POST";
	
	@Override
	public String verify(String channelId, String token, Map<String, Object> extraParams) {
		// 联想参数取得
		Object gameId = extraParams.get("gameId");
		
		GameChannelSetting channelSetting = VerifySessionBO.getGameChannelSetting(gameId.toString(), channelId);
		
		if(channelSetting == null){
			logger.error("JinliVerifyService|method=verify|error=jinli参数错误，gameId为空");
			
			return "";
		}
		
		String apiKey    = channelSetting.getAppKey().trim();     //替换成商户申请获取的APIKey
		String secretKey = channelSetting.getAppSecret().trim();  //替换成商户申请获取的SecretKey
		
		HttpUtil httpUtil = new HttpUtil();
		
		String auth = builderAuthorization(apiKey, secretKey);
		
		//获取接口返回结果
		String res = httpUtil.sendHttpsPostAuthorization(verify_url, token.replace("#@#", "\""), auth);
		
		if(StringUtils.isBlank(res)){
			logger.error("JinliVerifyService|method=verify|error=金立验证错误，res为空");
			
			return "";
		}
		
		logger.error("JinliVerifyService|method=verify|res="+res);
		
		try{
			Map result = JSON.parseObject(res, Map.class);
			
			// 验证接口返回的数据是json格式，如果里面包含有“r”参数，则认为验证失败，否则成功
			if(result.get("r") == null) {
				
				return result.get("u").toString();
			}else{
				logger.error("JinliVerifyService|method=verify|error="+result.get("r").toString());
			}
			
		}catch(Exception e){
			logger.error("JinliVerifyService|method=verify|error="+e.getMessage());
		}
		
		return null;
	}

//	private String builderAuthorization(String apiKey, String secretKey) {
//		Long ts = System.currentTimeMillis() / 1000;
//		
//		String nonce = StringUtils.getUniqueID(8);
//		
//		String mac = macSig(host, port, secretKey, ts.toString(), StringUtils.getUniqueID(8), method, url);
//		
//		mac = mac.replace("\n", "");
//		
//		StringBuilder authStr = new StringBuilder();
//		
//		authStr.append("MAC ");
//		
//		authStr.append(String.format("id=\"%s\"", apiKey));
//		
//		authStr.append(String.format(",ts=\"%s\"", ts));
//		authStr.append(String.format(",nonce=\"%s\"", nonce));
//		authStr.append(String.format(",mac=\"%s\"", mac));
//		
//		return authStr.toString();
//	}
//	
//	private String macSig(String host, String port, String macKey, String timestamp, String nonce, String method, String uri) {
//		// 1. build mac string
//		// 2. hmac-sha1
//		// 3. base64-encoded
//		StringBuffer buffer = new StringBuffer();
//		buffer.append(timestamp).append("\n");
//		buffer.append(nonce).append("\n");
//		buffer.append(method.toUpperCase()).append("\n");
//		buffer.append(uri).append("\n");
//		buffer.append(host.toLowerCase()).append("\n");
//		buffer.append(port).append("\n");
//		buffer.append("\n");
//		
//		String text = buffer.toString();
//
//		byte[] ciphertext = null;
//		try {
//			ciphertext = HashKit.HmacSHA1EncryptByte(text, macKey);
//		} catch (Throwable e) {
//			e.printStackTrace();
//			return null;
//		}
//
//		String sigString = Base64Utils.encode(ciphertext);
//		
//		return sigString;
//	}
	
	private String builderAuthorization(String apiKey, String secretKey) {
		
		Long ts = System.currentTimeMillis() / 1000;
		String nonce = StringUtil.randomStr().substring(0, 8).toLowerCase();
		String mac = CryptoUtility.macSig(host, port, secretKey, ts.toString(), nonce, method, url);
		mac = mac.replace("\n", "");
		StringBuilder authStr = new StringBuilder();
		authStr.append("MAC ");
		authStr.append(String.format("id=\"%s\"", apiKey));
		authStr.append(String.format(",ts=\"%s\"", ts));
		authStr.append(String.format(",nonce=\"%s\"", nonce));
		authStr.append(String.format(",mac=\"%s\"", mac));
		return authStr.toString();
	}
	
	static class CryptoUtility {

		private static final String MAC_NAME = "HmacSHA1";

		public static String macSig(String host, String port, String macKey, String timestamp, String nonce, String method, String uri) {
			// 1. build mac string
			// 2. hmac-sha1
			// 3. base64-encoded

			StringBuffer buffer = new StringBuffer();
			buffer.append(timestamp).append("\n");
			buffer.append(nonce).append("\n");
			buffer.append(method.toUpperCase()).append("\n");
			buffer.append(uri).append("\n");
			buffer.append(host.toLowerCase()).append("\n");
			buffer.append(port).append("\n");
			buffer.append("\n");
			String text = buffer.toString();

			byte[] ciphertext = null;
			try {
				ciphertext = hmacSHA1Encrypt(macKey, text);
			} catch (Throwable e) {
				e.printStackTrace();
				return null;
			}

			String sigString = Base64Utils.encode(ciphertext);
			return sigString;
		}

		public static byte[] hmacSHA1Encrypt(String encryptKey, String encryptText) throws InvalidKeyException, NoSuchAlgorithmException {
			Mac mac = Mac.getInstance(MAC_NAME);
			mac.init(new SecretKeySpec(StringUtil.getBytes(encryptKey), MAC_NAME));
			return mac.doFinal(StringUtil.getBytes(encryptText));
		}

	}

    static class StringUtil {
		public static final String UTF8 = "UTF-8";
		private static final byte[] BYTEARRAY = new byte[0];

		public static boolean isNullOrEmpty(String s) {
			if (s == null || s.isEmpty() || s.trim().isEmpty())
				return true;
			return false;
		}

		public static String randomStr() {
			return CamelUtility.uuidToString(UUID.randomUUID());
		}

		public static byte[] getBytes(String value) {
			return getBytes(value, UTF8);
		}

		public static byte[] getBytes(String value, String charset) {
			if (isNullOrEmpty(value))
				return BYTEARRAY;
			if (isNullOrEmpty(charset))
				charset = UTF8;
			try {
				return value.getBytes(charset);
			} catch (UnsupportedEncodingException e) {
				return BYTEARRAY;
			}
		}
	}

	static class CamelUtility {
		public static final int SizeOfUUID = 16;
		private static final int SizeOfLong = 8;
		private static final int BitsOfByte = 8;
		private static final int MBLShift = (SizeOfLong - 1) * BitsOfByte;

		private static final char[] HEX_CHAR_TABLE = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

		public static String uuidToString(UUID uuid) {
			long[] ll = {uuid.getMostSignificantBits(), uuid.getLeastSignificantBits()};
			StringBuilder str = new StringBuilder(SizeOfUUID * 2);
			for (int m = 0; m < ll.length; ++m) {
				for (int i = MBLShift; i > 0; i -= BitsOfByte)
					formatAsHex((byte) (ll[m] >>> i), str);
				formatAsHex((byte) (ll[m]), str);
			}
			return str.toString();
		}

		public static void formatAsHex(byte b, StringBuilder s) {
			s.append(HEX_CHAR_TABLE[(b >>> 4) & 0x0F]);
			s.append(HEX_CHAR_TABLE[b & 0x0F]);
		}

	}
}
