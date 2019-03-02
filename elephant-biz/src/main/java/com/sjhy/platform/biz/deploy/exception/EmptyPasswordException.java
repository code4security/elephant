package com.sjhy.platform.biz.deploy.exception;

/**
 * 密码为空
 * 
 * @author Xiong
 * 
 */
public class EmptyPasswordException extends Exception
{
	private static final long serialVersionUID = 1L;

	public EmptyPasswordException()
	{
	}

	public EmptyPasswordException( String message )
	{
		super( message );
	}

	public EmptyPasswordException( Throwable cause )
	{
		super( cause );
	}

	public EmptyPasswordException(String message, Throwable cause )
	{
		super( message, cause );
	}
}