package com.sjhy.platform.biz.deploy.exception;

/**
 * 送过来的sessionKey与redis中的sessionKey不一致,请重新登陆
 * @author SHACS
 *
 */
public class PleaseLoginAgainException extends Exception {
	private static final long serialVersionUID = 1L;
	public PleaseLoginAgainException() {
	}
	public PleaseLoginAgainException(String message) {
		super(message);
	}
	public PleaseLoginAgainException(Throwable cause) {
		super(cause);
	}
	public PleaseLoginAgainException(String message, Throwable cause) {
		super(message, cause);
	}
}
