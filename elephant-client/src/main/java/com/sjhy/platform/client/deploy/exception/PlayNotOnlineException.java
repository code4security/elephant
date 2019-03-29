package com.sjhy.platform.client.deploy.exception;

/**
 * 
 * <p>类说明:玩家不在线</p>
 * <p>文件名： PlayNotOnlineException.java</p>
 * <p>创建人及时间：	宋士龙 2012-8-17</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 *
 */
public class PlayNotOnlineException extends Exception {
	private static final long serialVersionUID = 1L;
	public PlayNotOnlineException(){
	}
	public PlayNotOnlineException(String message){
		super(message);
	}
	public PlayNotOnlineException(Throwable cause){
		super(cause);
	}
	public PlayNotOnlineException(String message, Throwable cause){
		super(message,cause);
	}
}	
