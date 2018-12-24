package com.sjhy.platform.biz.exception;

/**
 * 账户名为空
 * 
 * @author Xiong
 * 
 */
public class EmptyAccountNameException extends Exception
{
	private static final long serialVersionUID = 1L;

	public EmptyAccountNameException()
	{
	}

	public EmptyAccountNameException( String message )
	{
		super( message );
	}

	public EmptyAccountNameException( Throwable cause )
	{
		super( cause );
	}

	public EmptyAccountNameException(String message, Throwable cause )
	{
		super( message, cause );
	}
}