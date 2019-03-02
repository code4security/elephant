package com.sjhy.platform.biz.pay;

import java.util.Properties;

//import javax.annotation.Resource;

import com.sjhy.platform.biz.deploy.config.AppConfig;
import com.sjhy.platform.biz.deploy.config.GamePayConfig;
import com.sjhy.platform.biz.deploy.utils.DESUtil;
import com.sjhy.platform.biz.deploy.utils.GetBeanHelper;
import com.sjhy.platform.biz.deploy.utils.HttpUtil;
import com.sjhy.platform.biz.deploy.utils.MD5Util;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/** 
 * <p>类说明:</p>
 * <p>文件名： AbstractPaykairoProxy.java</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 **/
@Service("AbstractGamePayProxy")
public abstract class AbstractGamePayProxy<T> {
	private static Logger log = Logger.getLogger(AbstractGamePayProxy.class);

	private MD5Util md5Util=new MD5Util();
	
	public abstract String getAction();
	
	public abstract String getData(String[] datas);
	
	public abstract T parseResponse(JSONObject responseJson);
	
	private String key;
	private String cno;
	private String gamePayURL;
	
	public AbstractGamePayProxy(){
		Properties serverConfig = GetBeanHelper.getServerConfig();
		
		gamePayURL = serverConfig.getProperty(AppConfig.GAME_PAY_URL);
		key = serverConfig.getProperty(AppConfig.GAME_PAY_KEY);
		
		String gameId   = serverConfig.getProperty(AppConfig.GAME_ID);
		String serverId = serverConfig.getProperty(AppConfig.SERVER_ID);
		
		cno = gameId+"-"+serverId;
	}
	
	public T sendPost(String[] datas){
		JSONObject responseJson = null;
		
		HttpUtil httpUtil = new HttpUtil();
		try {
			DESUtil desUtil = new DESUtil(key);
			
			String action = getAction();
			String data = getData(datas);
			
			log.info("key=============="+key);
			log.info("action==========="+action);
			log.info("cno=============="+cno);
			log.info("data============="+data);
			
			// 加密
			String postData = desUtil.getEncAndBase64String(data);
			
			String sign = "action="+action+"&data="+postData+"&cno="+cno+key;
			
			sign = md5Util.md5Hex(sign);
			
			String[][] postParams = new String[4][2];
			
			postParams[0][0] = "action";
			postParams[0][1] = action;
			postParams[1][0] = "data";
			postParams[1][1] = postData;
			postParams[2][0] = "cno";
			postParams[2][1] = cno;
			postParams[3][0] = "sign";
			postParams[3][1] = sign;
			
			if(/*GamePayConfig.ACTION_ADD_YYB_ORDER.equals(action) || */GamePayConfig.ACTION_ADD_TX_ORDER.equals(action)){
				// 单独服务器处理
				Properties serverConfig = GetBeanHelper.getServerConfig();
				String yybPayUrl = serverConfig.getProperty(AppConfig.GAME_PAY_URL_YYB);
				if(yybPayUrl != null && yybPayUrl.trim().length() > 0){
					gamePayURL = yybPayUrl;
				}
			}
			System.out.println("=============gamePayURL="+gamePayURL);
			String responseContent = httpUtil.postRequest(gamePayURL, postParams);
			if (responseContent != null){
				log.info("responseContent="+responseContent);
				
				String responseJsonData = desUtil.getDesAndBase64String(responseContent);
				
				log.info("responseJsonData="+responseJsonData);
				
				responseJson = new JSONObject(responseJsonData);
				
				return parseResponse(responseJson);
			}else{
				System.out.println("======3=======gamePayURL="+gamePayURL);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		
		return null;
	}
}
