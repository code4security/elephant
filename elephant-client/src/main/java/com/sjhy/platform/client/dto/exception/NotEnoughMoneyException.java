package com.sjhy.platform.client.dto.exception;

/**
 * 金钱不足
 * @author SHACS
 *
 */
public class NotEnoughMoneyException extends Exception {
	private static final long serialVersionUID = 1L;
	public NotEnoughMoneyException() {
		super();
	}
	public NotEnoughMoneyException(String message) {
		super(message);
	}
	public NotEnoughMoneyException(Throwable cause) {
		super(cause);
	}
	public NotEnoughMoneyException(String message, Throwable cause) {
		super(message, cause);
	}
}
