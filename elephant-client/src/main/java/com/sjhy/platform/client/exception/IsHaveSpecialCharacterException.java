package com.sjhy.platform.client.exception;

/**
 * 设备标识码都为空
 * @author tianfengshuo
 *
 */
public class IsHaveSpecialCharacterException extends Exception {
	private static final long serialVersionUID = 1L;
	public IsHaveSpecialCharacterException(){
	}	
	public IsHaveSpecialCharacterException(String message){
		super(message);
	}
	public IsHaveSpecialCharacterException(Throwable cause){
		super(cause);
	}
	public IsHaveSpecialCharacterException(String message, Throwable cause){
		super(message,cause);
	}
}
