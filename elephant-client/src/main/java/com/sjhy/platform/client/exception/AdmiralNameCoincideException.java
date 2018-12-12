package com.sjhy.platform.client.exception;

/**
 * 舰队长重名(指定的舰队长名已存在,请换一个舰队长名)
 * @author SHACS
 *
 */
public class AdmiralNameCoincideException extends Exception {
	private static final long serialVersionUID = 1L;
	public AdmiralNameCoincideException() {
	}
	public AdmiralNameCoincideException(String message) {
		super(message);
	}
	public AdmiralNameCoincideException(Throwable cause) {
		super(cause);
	}
	public AdmiralNameCoincideException(String message, Throwable cause) {
		super(message, cause);
	}
}
