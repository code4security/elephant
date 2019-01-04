package com.sjhy.platform.biz.verify;

import java.util.Map;

import javax.annotation.Resource;

import com.sjhy.platform.biz.bo.VerifySessionBO;
import com.sjhy.platform.client.dto.game.GameChannelSetting;
import com.sjhy.platform.client.dto.utils.Base64Utils;
import com.sjhy.platform.client.dto.utils.HttpUtil;
import com.sjhy.platform.client.dto.utils.MD5Util;
import com.sjhy.platform.client.dto.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service("1500")
public class BaiduVerifyService implements IVerifySession{
	private static final Logger logger = Logger.getLogger( BaiduVerifyService.class );
	
	@Override
	public String verify(String channelId, String token, Map<String, Object> extraParams) {
		
		// 联想参数取得
		Object gameId = extraParams.get("gameId");
		
		GameChannelSetting channelSetting = VerifySessionBO.getGameChannelSetting(gameId.toString(), channelId);
		
		if(channelSetting == null){
			logger.error("BaiduVerifyService|method=verify|error=baidu参数错误，gameId为空");
			
			return "";
		}
		
		String sign = MD5Util.me().md5Hex(channelSetting.getAppKey().trim() + token + channelSetting.getAppSecret().trim());//签名
		
		// SDK 版本号 版本号 <3.6.0、http://querysdkapi.91.com/cploginstatequery.ashx?
		// SDK 版本号 版本号 >=3.6.0、http://querysdkapi.baidu.com/query/cploginstatequery?
		String goUrl ="http://querysdkapi.91.com/CpLoginStateQuery.ashx";//接口地址

		StringBuilder param = new StringBuilder();
		param.append("AppID=");
		param.append(channelSetting.getAppKey().trim());
		param.append("&AccessToken=");
		param.append(token);
		param.append("&Sign=");
		param.append(sign.toLowerCase());
		
		HttpUtil httpUtil = new HttpUtil();
		
		//获取接口返回结果
		String res = httpUtil.sendHttpsPost(goUrl, param.toString());
		
		if(StringUtils.isBlank(res)){
			logger.error("BaiduVerifyService|method=verify|error=baidu验证错误，res为空");
			
			return "";
		}
		
		try{
			Map result = JSON.parseObject(res, Map.class);
			
			//ResultCode=1则代表接口返回信息成功
			if("1".equals(result.get("ResultCode").toString())) {
				String ResultCode = result.get("ResultCode").toString();
				String Content = result.get("Content").toString();
				String Sign = result.get("Sign").toString();
				
				String VerifySign = MD5Util.me().md5Hex(channelSetting.getAppKey() + ResultCode + java.net.URLDecoder.decode(Content,"utf-8") + channelSetting.getAppSecret());
				
				if(VerifySign.toLowerCase().equals(Sign.toLowerCase())){
				
					logger.error("BaiduVerifyService|method=verify|error=baidu验证OK，");
					
					// Content参数需要URLDecoder
					Content = java.net.URLDecoder.decode(Content, "utf-8");  
			    
					// Base64解码
					String jsonStr = Base64Utils.decode(Content);
					
					logger.error("BaiduVerifyService|method=verify|error=baidu验证OK_content="+jsonStr);
					
					// json解析
					Map _result = JSON.parseObject(jsonStr, Map.class);
					
					//根据获取的信息，执行业务处理
					return _result.get("UID").toString();
				}else{
					logger.error("BaiduVerifyService|method=verify|error=签名验证错误");
				}
			}else{
				logger.error("BaiduVerifyService|method=verify|error=" + result.get("ResultMsg").toString());
			}
		}catch(Exception e){
			logger.error("BaiduVerifyService|method=verify|error="+e.getMessage());
		}
			
		return null;
	}

}
