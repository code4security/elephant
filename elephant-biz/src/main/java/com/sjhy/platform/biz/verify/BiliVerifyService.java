package com.sjhy.platform.biz.verify;

import com.alibaba.fastjson.JSON;
import com.sjhy.platform.biz.bo.VerifySessionBO;
import com.sjhy.platform.biz.utils.HttpUtil;
import com.sjhy.platform.client.dto.game.GameChannelSetting;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Service("2900")
public class BiliVerifyService implements IVerifySession{
	private static final Logger logger = LoggerFactory.getLogger( BiliVerifyService.class );
	
	private static final String url       = "http://pnew.biligame.net/api/server/session.verify";
	private static final String retry_url = "http://pserver.bilibiligame.net/api/server/session.verify";
	
	@Override
	public String verify(String channelId, String token, Map<String, Object> extraParams) {
		// 联想参数取得
		Object gameId = extraParams.get("gameId");
		
		GameChannelSetting channelSetting = VerifySessionBO.getGameChannelSetting(gameId.toString(), channelId);
		
		if(channelSetting == null){
			logger.error("BiliVerifyService|method=verify|error=bili参数错误，gameId为空");
			
			return "";
		}
		
		Object uid = extraParams.get("verifyId");
		if(uid == null){
			logger.error("BiliVerifyService|method=verify|error=bili参数错误，uid为空");
			
			return "";
		}
		
		Map<String, String> paramMaps = new HashMap<String, String>();
        paramMaps.put("access_key", token);
        
		// B站用户ID
		HttpUtil httpUtil = new HttpUtil();
		
		List<NameValuePair> nvps = null;
        try {
            if (paramMaps != null) {
                nvps = getNvpsByMap(paramMaps);
            }

            // 公共参数
            nvps.add(new NameValuePair("server_id", channelSetting.getServerId()));
            nvps.add(new NameValuePair("game_id", channelSetting.getAppId()));
            nvps.add(new NameValuePair("merchant_id", channelSetting.getMerchantId()));
            nvps.add(new NameValuePair("version", "1"));
            nvps.add(new NameValuePair("timestamp", String.valueOf(System.currentTimeMillis())));
            nvps.add(new NameValuePair("sign", getSign(nvps, channelSetting.getAppSecret())));

            String resp = httpUtil.postRequest(url, nvps);
            
            logger.error("BiliVerifyService|resp=" + resp);
            
            if(resp != null) {
            	return getOpenId(resp);
            }
        } catch (Exception e) {
        	if(e instanceof ConnectTimeoutException) {
        		// 重新连接
        		String resp = httpUtil.postRequest(retry_url, nvps);
        		
        		return getOpenId(resp);
        	}
        	
        	logger.error("BiliVerifyService|error=" + e.getMessage());
        } finally {
        }
        
		return null;
	}
	
	private String getOpenId(String resp) {
		@SuppressWarnings("unchecked")
		Map<String, Object> json = JSON.parseObject(resp, Map.class);
    	
    	if("0".equals(json.get("code").toString())) {
    		//return json.get("open_id").toString() + ":" + json.get("uname").toString();
    		return json.get("open_id").toString();
    	}
    	
    	return null;
	}
	
    /**
     * 把map转换为List<NameValuePair>
     * @param params
     * @return
     */
    private List<NameValuePair> getNvpsByMap(Map<String, String> params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>(params.size());
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value == null) {
                value = "";
            }
            
            nvps.add(new NameValuePair(key, value));
        }
        return nvps;
    }
    
	/**
     * 签名逻辑
     *
     * @param paramsTreeMap
     * @param secretKey
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getSign(TreeMap<String, Object> paramsTreeMap, String secretKey) throws UnsupportedEncodingException {
        StringBuffer signCalc = new StringBuffer();
        for (Map.Entry<String, Object> entry : paramsTreeMap.entrySet()) {
            //忽略不需要做签名的字段
            if (entry.getKey().equalsIgnoreCase("item_name")
                    || entry.getKey().equalsIgnoreCase("item_desc")
                    || entry.getKey().equalsIgnoreCase("token")
                    || entry.getKey().equalsIgnoreCase("sign")) {
                continue;
            }
            signCalc.append(String.valueOf(entry.getValue()));
        }
        return DigestUtils.md5Hex(signCalc.append(secretKey).toString());
    }

    /**
     * 校验签名
     * @param params
     * @param appSecret
     * @param sign
     * @return
     * @throws UnsupportedEncodingException
     */
    public static boolean checkSign(TreeMap<String, Object> params, String appSecret, String sign) throws UnsupportedEncodingException {
        return sign.equals(getSign(params, appSecret));
    }

    public static String getSign(List<NameValuePair> nvps, String appSecret) throws UnsupportedEncodingException {
        TreeMap<String, Object> paramsTreeMap = new TreeMap<String, Object>();
        for (NameValuePair nameValuePair : nvps) {
            paramsTreeMap.put(nameValuePair.getName(), nameValuePair.getValue());
        }
        return getSign(paramsTreeMap, appSecret);
    }
}
