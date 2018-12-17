package com.sjhy.platform.biz.exception;

/**
 * 聊天信息不允许包含被和谐词
 * @author SHACS
 *
 */
public class MessageIncludeHarmonyException extends Exception {
	private static final long serialVersionUID = 1L;
	public MessageIncludeHarmonyException() {
	}
	public MessageIncludeHarmonyException(String message) {
		super(message);
	}
	public MessageIncludeHarmonyException(Throwable cause) {
		super(cause);
	}
	public MessageIncludeHarmonyException(String message, Throwable cause) {
		super(message, cause);
	}
}
