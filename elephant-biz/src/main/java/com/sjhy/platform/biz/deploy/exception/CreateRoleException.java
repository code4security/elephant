package com.sjhy.platform.biz.deploy.exception;

public class CreateRoleException extends Exception {
	private static final long serialVersionUID = 1L;
	public CreateRoleException(){
	}
	public CreateRoleException(String message){
		super(message);
	}
	public CreateRoleException(Throwable cause){
		super(cause);
	}
	public CreateRoleException(String message, Throwable cause){
		super(message,cause);
	}
}
