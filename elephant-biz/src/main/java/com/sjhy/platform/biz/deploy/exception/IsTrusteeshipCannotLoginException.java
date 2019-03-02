package com.sjhy.platform.biz.deploy.exception;

/**
 * 账号被托管状态，不能登录
 * 
 * @author Xiong
 * 
 */
public class IsTrusteeshipCannotLoginException extends Exception
{
	private static final long serialVersionUID = 1L;

	public IsTrusteeshipCannotLoginException()
	{
	}

	public IsTrusteeshipCannotLoginException( String message )
	{
		super( message );
	}

	public IsTrusteeshipCannotLoginException( Throwable cause )
	{
		super( cause );
	}

	public IsTrusteeshipCannotLoginException(String message, Throwable cause )
	{
		super( message, cause );
	}
}