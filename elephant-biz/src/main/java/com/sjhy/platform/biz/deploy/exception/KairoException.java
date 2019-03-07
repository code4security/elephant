package com.sjhy.platform.biz.deploy.exception;

import com.google.protobuf.InvalidProtocolBufferException;
import com.sjhy.platform.biz.deploy.config.KairoErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class KairoException extends Exception {
	private static final Logger log = LoggerFactory.getLogger(KairoException.class);
	private static final long serialVersionUID = 1L;
	private KairoErrorCode kairoErrorCode = KairoErrorCode.OK;
	public KairoException(KairoErrorCode kairoErrorCode) {
		this.kairoErrorCode = kairoErrorCode;
	}
	public KairoException(KairoErrorCode kairoErrorCode, String message) {
		super(message);
		this.kairoErrorCode = kairoErrorCode;
	}
	public KairoException(KairoErrorCode kairoErrorCode,Throwable cause) {
		super(cause);
		this.kairoErrorCode = kairoErrorCode;
	}
	public KairoException(KairoErrorCode kairoErrorCode, String message, Throwable cause) {
		super(message, cause);
		this.kairoErrorCode = kairoErrorCode;
	}
	public KairoErrorCode getkairoErrorCode() {
		return kairoErrorCode;
	}
	public int getErrorCode() {
		return kairoErrorCode.getErrorCode();
	}
	public String getDesc() {
		return kairoErrorCode.getDesc();
	}
	
	private static KairoErrorCode searchErrorCode(Exception e) {
		if(e instanceof InvalidProtocolBufferException){
			 return KairoErrorCode.ERROR_PARAM;
		}else if(e instanceof RuntimeException){
			 return KairoErrorCode.ERROR_UNKNOW;
		}else if(e instanceof IOException){
			 return KairoErrorCode.ERROR_UNKNOW;
		}else if(e instanceof AdmiralNameIsNotNullableException){
			 return KairoErrorCode.ADMIRAL_NAME_IS_NOT_NULLABLE;
		}else if(e instanceof AdmiralNameIsTooLongException){
			 return KairoErrorCode.ADMIRAL_NAME_IS_TOO_LONG;
		}else if(e instanceof AdmiralNameIncludeHarmonyException){
			 return KairoErrorCode.ADMIRAL_NAME_INCLUDE_HARMONY;
		}else if(e instanceof NotEnoughMoneyException){
			 return KairoErrorCode.NOT_ENOUGH_MONEY;
		}else if(e instanceof NotEnoughIngotException){
			 return KairoErrorCode.NOT_ENOUGH_INGOT;
		}else if(e instanceof NoSuchRoleException){
			 return KairoErrorCode.NO_SUCH_ROLE;
		}else if(e instanceof AdmiralNameCoincideException){
			 return KairoErrorCode.ADMIRAL_NAME_COINCIDE;
		}else if(e instanceof AlreadyExistsPlayerRoleException){
			 return KairoErrorCode.ROLE_ALREADY_EXISTS;
		}else if(e instanceof AdmiralNameIsNotNullableException){
			 return KairoErrorCode.ADMIRAL_NAME_IS_NOT_NULLABLE;
		}else if(e instanceof AdmiralNameIsTooLongException){
			 return KairoErrorCode.ADMIRAL_NAME_IS_TOO_LONG;
		}else if(e instanceof AdmiralNameIncludeHarmonyException){
			 return KairoErrorCode.ADMIRAL_NAME_INCLUDE_HARMONY;
		}else if(e instanceof CreateRoleException){
			return KairoErrorCode.CREATE_ROLE_ERROR;
		}else if(e instanceof DeviceSignNullException){
			return KairoErrorCode.DEVICE_SIGN_NULL;
		}else if(e instanceof EmptyAccountNameException){
			return KairoErrorCode.EMPTY_ACCOUNT_NAME;
		}else if(e instanceof AccountAlreadyBindingOtherException){
			return KairoErrorCode.ACCOUNT_ALREADY_BIND_OTHER;
		}else if(e instanceof NotExistAccountException){
			return KairoErrorCode.NOT_EXIST_ACCOUNT;
		}else if(e instanceof NotChallengeYetException){
			return KairoErrorCode.NOT_CHALLENGE_YET;
		}else if(e instanceof FreezeTheAccountException){
			return KairoErrorCode.FREEZE_THE_ACCOUNT;
		}else if(e instanceof IpIsNotInWhiteListException){
			return KairoErrorCode.IP_IS_NOT_IN_WHITELIST;
		}else if(e instanceof GameIdIsNotExsitsException){
			return KairoErrorCode.GAMEID_IS_NOT_EXISTS;
		}else if(e instanceof OssBucketkeyException){
			return KairoErrorCode.OSS_BUCKET_KEY_ERROR;
		}else if(e instanceof IsHaveSpecialCharacterException){
			return KairoErrorCode.IS_HAVE_SPECIAL_CHARACTER;
		}else if(e instanceof MailContextTooLongException){
			return KairoErrorCode.ERROR_MAIL_CONTEXT_TOO_LONG;
		}else if(e instanceof MailItemErrorException){
			return KairoErrorCode.ERROR_MAIL_ITEM;
		}else if(e instanceof MailNotBelongThisRoleException){
			return KairoErrorCode.ERROR_NOT_BELONG_THIS_ROLE;
		}
		
		return KairoErrorCode.ERROR_PARAM;
	}
	
	/**
	 * tzz added for logout Exception and return error code to Client
	 * @param e
	 * @param roleId
	 * @return error code for client (ProtocolConfig)
	 */
	public static int logoutException(Exception e, long roleId) {
		KairoErrorCode errorCode;
		
		if(e instanceof KairoException){
			errorCode = ((KairoException)e).getkairoErrorCode();
		}else{
			errorCode = searchErrorCode(e);
		}
		
		if(errorCode.isWriteLog())
			log.error(errorCode.getDesc()+";RoleId[" + roleId + "] 发现错误：ErrorCode-" + errorCode, e);
		else
			log.error(errorCode.getDesc()+";RoleId[" + roleId + "] 发现错误：ErrorCode-" + errorCode);
		
		return errorCode.getErrorCode();		
	}
}
