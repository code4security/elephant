package com.sjhy.platform.biz.deploy.exception;

/**
 * 账号名已存在
 * 
 * @author Xiong
 * 
 */
public class ActivationCodeIsValidException extends Exception
{
	private static final long serialVersionUID = 1L;

	public ActivationCodeIsValidException()
	{
	}

	public ActivationCodeIsValidException( String message )
	{
		super( message );
	}

	public ActivationCodeIsValidException( Throwable cause )
	{
		super( cause );
	}

	public ActivationCodeIsValidException(String message, Throwable cause )
	{
		super( message, cause );
	}
}