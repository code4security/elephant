package com.sjhy.platform.client.deploy.exception;

public class MailContextTooLongException extends Exception {
	private static final long serialVersionUID = 1L;
	public MailContextTooLongException() {
	}

	public MailContextTooLongException(String message) {
		super(message);
	}

	public MailContextTooLongException(Throwable cause) {
		super(cause);
	}

	public MailContextTooLongException(String message, Throwable cause) {
		super(message, cause);
	}
}
