package com.sjhy.platform.biz.verify;

import java.util.Date;
import java.util.Map;

import com.sjhy.platform.biz.bo.VerifySessionBO;
import com.sjhy.platform.client.dto.config.AppConfig;
import com.sjhy.platform.client.dto.enumerate.SubChannelEnum;
import com.sjhy.platform.client.dto.game.GameChannelSetting;
import com.sjhy.platform.client.dto.utils.GetBeanHelper;
import com.sjhy.platform.client.dto.utils.HttpUtil;
import com.sjhy.platform.client.dto.utils.MD5Util;
import com.sjhy.platform.client.dto.utils.StringUtils;
import com.sjhy.platform.persist.mysql.game.GameChannelSettingMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import javax.annotation.Resource;

/**
 * 应用宝渠道
 * @author Administrator
 *
 */
@Service("1100")
public class YybVerifyService implements IVerifySession{
	private static final Logger logger = Logger.getLogger( YybVerifyService.class );

	@Resource
	private GameChannelSettingMapper gameChannelSettingMapper;
	
	// 手Q验证接口
	private static final String QQ_DEBUG_YSDK_CHECK_TOKEN = "http://ysdktest.qq.com/auth/qq_check_token?timestamp=_timestamp_&appid=_appid_&sig=_sig_&openid=_openid_&openkey=_openkey_&userip";
	private static final String QQ_RELEASE_YSDK_CHECK_TOKEN = "http://ysdk.qq.com/auth/qq_check_token?timestamp=_timestamp_&appid=_appid_&sig=_sig_&openid=_openid_&openkey=_openkey_&userip";
	
	// 微信验证接口
	private static final String WX_DEBUG_YSDK_CHECK_TOKEN = "http://ysdktest.qq.com/auth/wx_check_token?timestamp=_timestamp_&appid=_appid_&sig=_sig_&openid=_openid_&openkey=_openkey_&userip";
	private static final String WX_RELEASE_YSDK_CHECK_TOKEN = "http://ysdk.qq.com/auth/wx_check_token?timestamp=_timestamp_&appid=_appid_&sig=_sig_&openid=_openid_&openkey=_openkey_&userip";
	
	@Override
	public String verify(String channelId, String token, Map<String, Object> extraParams) {
		// sub channel信息取得
		Object subChannelId = extraParams.get("subChannelId");
		
		Object openid = extraParams.get("verifyId");
		
		if(openid == null) {
			logger.error("YybVerifyService|method=verify|error=应用宝验证错误，openid为空");
			
			return "";
		}
		
		Object gameId = extraParams.get("gameId");
		
		HttpUtil httpUtil = new HttpUtil();
		
		// 参数设定
		String checkUrl = getCheckUrl(gameId.toString(),channelId, subChannelId.toString(), openid.toString(), token);
		logger.debug("YybVerifyService|method=verify|checkUrl="+checkUrl);
		
		String res = httpUtil.getRequest(checkUrl);
		if(StringUtils.isBlank(res)){
			logger.error("YybVerifyService|method=verify|error=应用宝验证错误，res为空");
			
			return "";
		}
		
		try{
			Map<String, Object> result = JSON.parseObject(res, Map.class);
			
			if(result != null){
				if(result.get("ret") != null && "0".equals(result.get("ret").toString())){
					return openid.toString();
				}else{
					logger.error("YybVerifyService|method=verify|error="+result.get("msg"));
				}
			}
		} catch (Exception e){
			logger.error("YybVerifyService|method=verify|error="+e.getMessage());
		}
		
		return null;
	}

	/**
	 * 取得验证url
	 * @param subChannelId
	 * @return
	 */
	private String getCheckUrl(String gameId, String channelId, String subChannelId, String openid, String openkey) {
		// 请求地址
		String checkUrl = "";
		
		// timestamp
		Date timestamp  = new Date();
		
		// 生产环境开关取得
		String debug = GetBeanHelper.getServerConfig().getProperty(AppConfig.SERVER_DEBUG, "false");

		if(subChannelId.equals("110001"+SubChannelEnum.YYB_QQ.getSubChannelId())){
			// qq参数获得
			GameChannelSetting channelSetting = VerifySessionBO.getGameChannelSetting(gameId, subChannelId);
			
			if(channelSetting == null){
				logger.error("YybVerifyService|method=verify|error=应用宝验证参数错误【qq】，channelSetting为空");
				
				return "";
			}
			
			String appkey = channelSetting.getAppKey();
			
			if(Boolean.valueOf(debug).booleanValue()) {
				checkUrl = QQ_DEBUG_YSDK_CHECK_TOKEN;
			}else{
				checkUrl = QQ_RELEASE_YSDK_CHECK_TOKEN;
			}
			
			// 签名算出
			String sign  = MD5Util.me().md5Hex(appkey + timestamp.getTime() / 1000);
			
			checkUrl = checkUrl.replace("_timestamp_", timestamp.getTime()/1000+"").replace("_appid_", channelSetting.getAppId()).replace("_sig_", sign).replace("_openid_", openid).replace("_openkey_", openkey);
		}else if(subChannelId.equals(""+SubChannelEnum.YYB_WX.getSubChannelId())){
			// wx参数获得
			GameChannelSetting channelSetting = VerifySessionBO.getGameChannelSetting(gameId, subChannelId);
			
			if(channelSetting == null){
				logger.error("YybVerifyService|method=verify|error=应用宝验证参数错误【qq】，channelSetting为空");
				
				return "";
			}
			
			String appkey = channelSetting.getAppKey();
			
			if(Boolean.valueOf(debug).booleanValue()) {
				checkUrl = WX_DEBUG_YSDK_CHECK_TOKEN;
			}else{
				checkUrl = WX_RELEASE_YSDK_CHECK_TOKEN;
			}
			
			// 签名算出
			String sign  = MD5Util.me().md5Hex(appkey + timestamp.getTime() / 1000);
						
			checkUrl = checkUrl.replace("_timestamp_", timestamp.getTime()/1000+"").replace("_appid_", channelSetting.getAppId()).replace("_sig_", sign).replace("_openid_", openid).replace("_openkey_", openkey);
		}
		
		return checkUrl;
	}
}
