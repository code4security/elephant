package com.sjhy.platform.biz.exception;

/**
 * 舰队长名超出允许长度
 * @author SHACS
 *
 */
public class AdmiralNameIsTooLongException extends Exception {
	private static final long serialVersionUID = 1L;
	public AdmiralNameIsTooLongException() {
	}
	public AdmiralNameIsTooLongException(String message) {
		super(message);
	}
	public AdmiralNameIsTooLongException(Throwable cause) {
		super(cause);
	}
	public AdmiralNameIsTooLongException(String message, Throwable cause) {
		super(message, cause);
	}
}
