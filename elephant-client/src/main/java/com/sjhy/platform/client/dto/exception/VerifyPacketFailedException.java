package com.sjhy.platform.client.dto.exception;

/**
 * @author Xiong
 *
 */
public class VerifyPacketFailedException extends Exception
{
	private static final long serialVersionUID = 1L;

	public VerifyPacketFailedException() 
	{
	}
	public VerifyPacketFailedException(String message)
	{
		super(message);
	}
	public VerifyPacketFailedException(Throwable cause)
	{
		super(cause);
	}
	public VerifyPacketFailedException(String message, Throwable cause)
	{
		super(message, cause);
	}
}