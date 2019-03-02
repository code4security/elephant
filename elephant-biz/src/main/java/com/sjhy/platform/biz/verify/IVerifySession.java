package com.sjhy.platform.biz.verify;

import org.springframework.stereotype.Component;

import java.util.Map;

/** 
 * <p>类说明:验证第三方session id</p>
 * <p>文件名： IVerifySession.java</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 **/
@Component
public interface IVerifySession {

	/**
	 * 第三方验证
	 * @param channelId 渠道
	 * @param token 渠道验证token
	 * @param extraParams 附加参数
	 * @return 第三方渠道用户id
	 * @throws Exception 
	 */
	public String verify(String channelId, String token, Map<String, Object> extraParams);
}
