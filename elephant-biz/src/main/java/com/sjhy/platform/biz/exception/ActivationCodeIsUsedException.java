package com.sjhy.platform.biz.exception;

/**
 * 账号名已存在
 * 
 * @author Xiong
 * 
 */
public class ActivationCodeIsUsedException extends Exception
{
	private static final long serialVersionUID = 1L;

	public ActivationCodeIsUsedException()
	{
	}

	public ActivationCodeIsUsedException( String message )
	{
		super( message );
	}

	public ActivationCodeIsUsedException( Throwable cause )
	{
		super( cause );
	}

	public ActivationCodeIsUsedException(String message, Throwable cause )
	{
		super( message, cause );
	}
}