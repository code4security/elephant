package com.sjhy.platform.biz.deploy.exception;

/**
 * 账号名已存在
 * 
 * @author Xiong
 * 
 */
public class IpIsNotInWhiteListException extends Exception
{
	private static final long serialVersionUID = 1L;

	public IpIsNotInWhiteListException()
	{
	}

	public IpIsNotInWhiteListException( String message )
	{
		super( message );
	}

	public IpIsNotInWhiteListException( Throwable cause )
	{
		super( cause );
	}

	public IpIsNotInWhiteListException(String message, Throwable cause )
	{
		super( message, cause );
	}
}