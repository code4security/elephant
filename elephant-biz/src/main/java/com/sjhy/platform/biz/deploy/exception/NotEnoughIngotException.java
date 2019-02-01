package com.sjhy.platform.biz.deploy.exception;

/**
 * 元宝不足
 * @author SHACS
 *
 */
public class NotEnoughIngotException extends Exception {
	private static final long serialVersionUID = 1L;
	public NotEnoughIngotException() {
	}
	public NotEnoughIngotException(String message) {
		super(message);
	}
	public NotEnoughIngotException(Throwable cause) {
		super(cause);
	}
	public NotEnoughIngotException(String message, Throwable cause) {
		super(message, cause);
	}
}
