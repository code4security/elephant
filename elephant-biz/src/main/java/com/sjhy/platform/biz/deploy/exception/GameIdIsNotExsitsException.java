package com.sjhy.platform.biz.deploy.exception;

public class GameIdIsNotExsitsException extends Exception {
	private static final long serialVersionUID = 1L;
	public GameIdIsNotExsitsException(){
	}
	public GameIdIsNotExsitsException(String message){
		super(message);
	}
	public GameIdIsNotExsitsException(Throwable cause){
		super(cause);
	}
	public GameIdIsNotExsitsException(String message, Throwable cause){
		super(message,cause);
	}
}
