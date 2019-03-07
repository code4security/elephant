package com.sjhy.platform.biz.verify;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import com.sjhy.platform.biz.bo.VerifySessionBO;
import com.sjhy.platform.client.dto.game.GameChannelSetting;
import com.sjhy.platform.biz.deploy.utils.HashKit;
import com.sjhy.platform.biz.deploy.utils.HttpUtil;
import org.apache.commons.httpclient.NameValuePair;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service("2300")
public class HuaweiVerifyService implements IVerifySession{
	private static final Logger logger = LoggerFactory.getLogger( HuaweiVerifyService.class );
	
	private final static String LOGIN_URL = " https://gss-cn.game.hicloud.com/gameservice/api/gbClientApi";

	@Override
	public String verify(String channelId, String token, Map<String, Object> extraParams) {
		// 联想参数取得
		Object gameId = extraParams.get("gameId");
		
		GameChannelSetting channelSetting = VerifySessionBO.getGameChannelSetting(gameId.toString(), channelId);
		
		if(channelSetting == null){
			logger.error("HuaweiVerifyService|method=verify|error=huawei参数错误，gameId为空");
			
			return "";
		}
		
		Object playerId = extraParams.get("verifyId");
		if(playerId == null){
			logger.error("HuaweiVerifyService|method=verify|error=huawei验证错误，playerId为空");
			
			return "";
		}
		
		// 华为渠道比较特殊、token里面会有一个ts参数，用于验签
		String[] params = token.split(":");
		if(params.length < 2){
			logger.error("HuaweiVerifyService|method=verify|error=huawei参数错误，token值不对");
			
			return "";
		}

		token     = params[0];
		
		String ts = params[1];
		
		String playerLevel = params[2];
		
		// CP请求参数
        Map<String,String> mockRequestParams = new HashMap<String, String>();
        
        mockRequestParams.put("method","external.hms.gs.checkPlayerSign");
        mockRequestParams.put("appId",channelSetting.getAppId());
        mockRequestParams.put("cpId",channelSetting.getCpId());
        mockRequestParams.put("ts",ts);
        mockRequestParams.put("playerId",playerId.toString());
        mockRequestParams.put("playerLevel",playerLevel);
        mockRequestParams.put("playerSSign",token);
        
		// 签名算出
		String sign = HashKit.sign(format(mockRequestParams), channelSetting.getPrivateKey().trim());
		
		mockRequestParams.put("cpSign", sign);
		
		HttpUtil httpUtil = new HttpUtil();
		
		List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();

        for (Map.Entry<String, String> entry : mockRequestParams.entrySet())
        {
            paramPairs.add(new NameValuePair(entry.getKey(), entry.getValue()));
        }
        
		String resp = httpUtil.postRequest(LOGIN_URL, paramPairs);
		
		logger.error("HuaweiVerifyService|method=verify|resp=" + resp);
		
		Map<String,Object> responseParamPairs = JSON.parseObject(resp);
		
		if("0".equals(responseParamPairs.get("rtnCode").toString())){// 验签成功
			return playerId.toString();
		}else{
			logger.error("HuaweiVerifyService|method=verify|error=huawei参数错误，签名验证错误.");
		}
		
		return null;
	}

    /**
     * 根据参数Map构造排序好的参数串
     *
     * @param params
     * @return
     */
    public static String format(Map<String, String> params)
    {
        StringBuffer base = new StringBuffer();
        Map<String, String> tempMap = new TreeMap<String, String>(params);

        // 获取计算nsp_key的基础串
        try
        {
            for (Map.Entry<String, String> entry : tempMap.entrySet())
            {
                String k = entry.getKey();
                String v = entry.getValue();
                base.append(k).append("=").append(URLEncoder.encode(v, "UTF-8")).append("&");
            }
        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println("Encode parameters failed.");
            e.printStackTrace();
        }

        String body = base.toString().substring(0, base.toString().length() - 1);
        // 空格和星号转义
        body = body.replaceAll("\\+", "%20").replaceAll("\\*", "%2A");

        return body;
    }
}
