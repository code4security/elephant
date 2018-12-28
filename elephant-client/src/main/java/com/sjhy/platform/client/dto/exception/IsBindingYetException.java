package com.sjhy.platform.client.dto.exception;

/**
 * @author 
 *
 */
public class IsBindingYetException extends Exception
{
	private static final long serialVersionUID = 1L;

	public IsBindingYetException() 
	{
	}
	public IsBindingYetException(String message)
	{
		super(message);
	}
	public IsBindingYetException(Throwable cause)
	{
		super(cause);
	}
	public IsBindingYetException(String message, Throwable cause)
	{
		super(message, cause);
	}
}