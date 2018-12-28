package com.sjhy.platform.client.dto.exception;

public class OssBucketkeyException extends Exception {
	private static final long serialVersionUID = 1L;
	public OssBucketkeyException(){
	}
	public OssBucketkeyException(String message){
		super(message);
	}
	public OssBucketkeyException(Throwable cause){
		super(cause);
	}
	public OssBucketkeyException(String message, Throwable cause){
		super(message,cause);
	}
}
