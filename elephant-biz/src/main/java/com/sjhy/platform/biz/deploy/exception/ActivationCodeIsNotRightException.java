package com.sjhy.platform.biz.deploy.exception;

/**
 * 账号名已存在
 * 
 * @author Xiong
 * 
 */
public class ActivationCodeIsNotRightException extends Exception
{
	private static final long serialVersionUID = 1L;

	public ActivationCodeIsNotRightException()
	{
	}

	public ActivationCodeIsNotRightException( String message )
	{
		super( message );
	}

	public ActivationCodeIsNotRightException( Throwable cause )
	{
		super( cause );
	}

	public ActivationCodeIsNotRightException(String message, Throwable cause )
	{
		super( message, cause );
	}
}