package com.sjhy.platform.biz.deploy.exception;

/**
 * 账号已经绑定其他设备，老设备用该账号登录时抛出的异常
 * @author SHACS
 *
 */
public class AccountAlreadyBindingOtherException extends Exception {
	private static final long serialVersionUID = 1L;
	public AccountAlreadyBindingOtherException() {
	}
	public AccountAlreadyBindingOtherException(String message) {
		super(message);
	}
	public AccountAlreadyBindingOtherException(Throwable cause) {
		super(cause);
	}
	public AccountAlreadyBindingOtherException(String message, Throwable cause) {
		super(message, cause);
	}
}
