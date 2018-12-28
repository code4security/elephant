package com.sjhy.platform.client.dto.exception;

/**
 * 账号名已存在
 * 
 * @author Xiong
 * 
 */
public class AlreadyExistAccountException extends Exception
{
	private static final long serialVersionUID = 1L;

	public AlreadyExistAccountException()
	{
	}

	public AlreadyExistAccountException( String message )
	{
		super( message );
	}

	public AlreadyExistAccountException( Throwable cause )
	{
		super( cause );
	}

	public AlreadyExistAccountException(String message, Throwable cause )
	{
		super( message, cause );
	}
}