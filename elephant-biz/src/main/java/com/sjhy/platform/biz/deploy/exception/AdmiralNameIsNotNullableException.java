package com.sjhy.platform.biz.deploy.exception;

/**
 * 舰队长名不允许为空
 * @author SHACS
 *
 */
public class AdmiralNameIsNotNullableException extends Exception {
	private static final long serialVersionUID = 1L;
	public AdmiralNameIsNotNullableException() {
	}
	public AdmiralNameIsNotNullableException(String message) {
		super(message);
	}
	public AdmiralNameIsNotNullableException(Throwable cause) {
		super(cause);
	}
	public AdmiralNameIsNotNullableException(String message, Throwable cause) {
		super(message, cause);
	}
}
