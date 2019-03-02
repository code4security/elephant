package com.sjhy.platform.biz.deploy.cache;

import com.sjhy.platform.client.dto.vo.LoginSessionVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component( "SessionCacheMgr" )
public class SessionCacheMgr
{
	@Resource
	private JCSCache<Integer, LoginSessionVO> sessionCache;

	public void put( int key, LoginSessionVO value )
	{
		sessionCache.put( key, value );
	}

	public LoginSessionVO get( int key )
	{
		return sessionCache.get( key );
	}

	public void remove( int key )
	{
		sessionCache.remove( key );
	}
}
