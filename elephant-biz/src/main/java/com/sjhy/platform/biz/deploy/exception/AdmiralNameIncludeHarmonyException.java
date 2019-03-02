package com.sjhy.platform.biz.deploy.exception;

/**
 * 舰队长名不允许包含特殊字符或被和谐词
 * @author SHACS
 *
 */
public class AdmiralNameIncludeHarmonyException extends Exception {
	private static final long serialVersionUID = 1L;
	public AdmiralNameIncludeHarmonyException() {
	}
	public AdmiralNameIncludeHarmonyException(String message) {
		super(message);
	}
	public AdmiralNameIncludeHarmonyException(Throwable cause) {
		super(cause);
	}
	public AdmiralNameIncludeHarmonyException(String message, Throwable cause) {
		super(message, cause);
	}
}
