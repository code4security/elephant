package com.sjhy.platform.client.dto.exception;

public class PhoneVerificationCodeException extends Exception {
	private static final long serialVersionUID = 1L;

	public PhoneVerificationCodeException()
	{
	}

	public PhoneVerificationCodeException( String message )
	{
		super( message );
	}

	public PhoneVerificationCodeException( Throwable cause )
	{
		super( cause );
	}

	public PhoneVerificationCodeException(String message, Throwable cause )
	{
		super( message, cause );
	}
}
