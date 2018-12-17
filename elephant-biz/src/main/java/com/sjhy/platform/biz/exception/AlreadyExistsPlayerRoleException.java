package com.sjhy.platform.biz.exception;

/**
 * 该玩家已经建立过角色了,不能再建立角色,请直接登录即可
 * @author SHACS
 *
 */
public class AlreadyExistsPlayerRoleException extends Exception {
	private static final long serialVersionUID = 1L;
	public AlreadyExistsPlayerRoleException() {
	}
	public AlreadyExistsPlayerRoleException(String message) {
		super(message);
	}
	public AlreadyExistsPlayerRoleException(Throwable cause) {
		super(cause);
	}
	public AlreadyExistsPlayerRoleException(String message, Throwable cause) {
		super(message, cause);
	}
}
