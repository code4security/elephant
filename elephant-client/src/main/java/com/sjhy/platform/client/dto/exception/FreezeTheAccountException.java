package com.sjhy.platform.client.dto.exception;

/**
 * 已冻结的账号,拒绝登陆
 * @author SHACS
 *
 */
public class FreezeTheAccountException extends Exception {
	private static final long serialVersionUID = 1L;
	public FreezeTheAccountException() {
	}
	public FreezeTheAccountException(String message) {
		super(message);
	}
	public FreezeTheAccountException(Throwable cause) {
		super(cause);
	}
	public FreezeTheAccountException(String message, Throwable cause) {
		super(message, cause);
	}
}
