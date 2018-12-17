package com.sjhy.platform.biz.exception;

/**
 * 设备标识码都为空
 * @author tianfengshuo
 *
 */
public class DeviceSignNullException extends Exception {
	private static final long serialVersionUID = 1L;
	public DeviceSignNullException(){
	}	
	public DeviceSignNullException(String message){
		super(message);
	}
	public DeviceSignNullException(Throwable cause){
		super(cause);
	}
	public DeviceSignNullException(String message, Throwable cause){
		super(message,cause);
	}
}
