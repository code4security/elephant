package com.sjhy.platform.biz.exception;

/**
 * 该设备已绑定账号,请用该账号登录
 * @author SHACS
 *
 */
public class AlreadyBindingException extends Exception {
	private static final long serialVersionUID = 1L;
	public AlreadyBindingException() {
	}
	public AlreadyBindingException(String message) {
		super(message);
	}
	public AlreadyBindingException(Throwable cause) {
		super(cause);
	}
	public AlreadyBindingException(String message, Throwable cause) {
		super(message, cause);
	}
}
