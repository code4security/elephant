package com.sjhy.platform.client.dto.exception;

/**
 * 账号不存在
 * 
 * @author Xiong
 * 
 */
public class NotExistAccountException extends Exception
{
	private static final long serialVersionUID = 1L;

	public NotExistAccountException()
	{
	}

	public NotExistAccountException( String message )
	{
		super( message );
	}

	public NotExistAccountException( Throwable cause )
	{
		super( cause );
	}

	public NotExistAccountException(String message, Throwable cause )
	{
		super( message, cause );
	}
}