package com.sjhy.platform.biz.deploy.exception;

/**
 * @author Xiong
 *
 */
public class NotChallengeYetException extends Exception
{
	private static final long serialVersionUID = 1L;

	public NotChallengeYetException() 
	{
	}
	public NotChallengeYetException(String message)
	{
		super(message);
	}
	public NotChallengeYetException(Throwable cause)
	{
		super(cause);
	}
	public NotChallengeYetException(String message, Throwable cause)
	{
		super(message, cause);
	}
}