package com.sjhy.platform.biz.exception;

/**
 * 传入的玩家id不合法
 * @author SHACS
 *
 */
public class NoSuchRoleException extends Exception {
	private static final long serialVersionUID = 1L;
	public NoSuchRoleException(){
	}
	public NoSuchRoleException(String message){
		super(message);
	}
	public NoSuchRoleException(Throwable cause){
		super(cause);
	}
	public NoSuchRoleException(String message, Throwable cause){
		super(message,cause);
	}
}	
