package com.sjhy.platform.biz.exception;

/**
 * 
 * <p>类说明:渠道的ID错误</p>
 * <p>文件名： ChannelIDErrorException.java</p>
 * <p>创建人及时间：	宋士龙 2013-5-28</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 *
 */
public class ChannelIDErrorException extends Exception {
	private static final long serialVersionUID = 1L;
	public ChannelIDErrorException() {
	}
	public ChannelIDErrorException(String message) {
		super(message);
	}
	public ChannelIDErrorException(Throwable cause) {
		super(cause);
	}
	public ChannelIDErrorException(String message, Throwable cause) {
		super(message, cause);
	}
}
