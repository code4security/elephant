package com.sjhy.platform.client.deploy.config;
/**
 * @HJ
 */
public enum KairoErrorCode {
	
	OK(10000,"",true),
	ERROR_UNKNOW(10001,"未知错误",true),
	ERROR_PARAM(10002,"参数错误",true),
	LOGIN_FAULT(10003,"登录失败",true),
	NO_SUCH_ROLE(10004,"玩家帐号不存在",true),
	ROLE_ALREADY_EXISTS(10005,"玩家帐号已经存在",true),
	
	ADMIRAL_NAME_IS_NOT_NULLABLE(10008,"角色名不允许为空",false),
	ADMIRAL_NAME_IS_TOO_LONG(10009,"角色名超出允许长度",false),
	ADMIRAL_NAME_INCLUDE_HARMONY(10010,"角色名不允许包含特殊字符或被和谐词",false),
	ADMIRAL_NAME_COINCIDE(10011,"角色重名(指定的角色名已存在,请换一个角色名)",false),
	CREATE_ROLE_ERROR(10018, "创建角色失败", false),
	VERSION_NEED_UPDATE(10037, "大版本号不正确", false),
	
	NOT_ENOUGH_MONEY(20001, "金钱不足",true),
	NOT_ENOUGH_INGOT(20002, "金币不足",true),
	OSS_BUCKET_KEY_ERROR(20003, "取得oss上传access key 异常", true),
	IS_HAVE_SPECIAL_CHARACTER(20004, "还有特殊字符", true),
	OSS_GAMEDATA_SPLIT_ERROR(20005, "Split出现错误", true),
	
	SRP_AUTHENTICATION_FAILED(30001, "SRP校验失败", true),
	NOT_CHALLENGE_YET(30002, "尚未进行第一次握手", true),
	NOT_EXIST_ACCOUNT(30003, "账号不存在", true),
	EMPTY_ACCOUNT_NAME(30004, "账号名为空", true),
	ALREADY_EXIST_ACCOUNT(30005, "账号名已存在", true),
	VERIFY_PACKET_FAILED(30006, "数据包校验失败", true),
	EMPTY_PASSWORD(30007, "密码为空", true),
	ACCOUNT_ALREADY_BIND_OTHER(30008, "账号已经绑定其他机器", true),
	ACCOUNT_ALREADY_BINDED(30009, "账号和该设备已经绑定 ", true),
	DEVICE_SIGN_NULL(30010, "imei和mac数值为空！ ", true),
	FREEZE_THE_ACCOUNT(30011, "已冻结的账号,拒绝登陆", true),
	
	IP_IS_NOT_IN_WHITELIST(30012, "IP地址没有在白名单中", true),//
	IS_NEED_UPGRADE(30013, "版本太久需要升级", false),
	ERROR_NO_GAME_NOTIFY_INFO(30014, "没有公告信息", false),
	GAMEID_IS_NOT_EXISTS(30015, "GAME id不存在", false),
	
	SAVE_DATA_TYPE_ERROR(30016, "存档种类错误", false),
	
	ERROR_PAY_NOT_GOODS(30017,"没有商品列表",false),
	ERROR_PAY_UID_ISNULL(30018,"玩家id为null",false),
	ERROR_PAY_GOOD_ID_ISNULL(30019,"商品id为null",true),
	ERROR_PAY_GOOD_ID_ERROR(30020,"商品id错误或不存在",true),
	ERROR_PAY_GOOD_NUMBER_ISNULL(30021,"商品数量为null",false),
	ERROR_PAY_ORDER_ID_ISNULL(30022,"订单id为null",false),
	ERROR_PAY_ORDER_ID_ERROR(30023,"订单id错误或不存在",false),
	ERROR_PAY_NO_PAY_INFO(30024,"没有支付信息",false),
	ERROR_PAY_POINT_ISNULL(30025,"支付点为null",false),
	ERROR_PAY_CHANNEL_ISNULL(30026,"支付渠道为null",false),
	ERROR_PAY_CHANNEL_ERROR(30027,"支付渠道错误或不存在",false),
	
	ERROR_KICKOUT_BY_GM(30028, "GM工具踢出", false),
	ERROR_KICKOUT_BY_SERVER(30029, "服务器自动踢出", false),
	
	ERROR_IS_BANED(30030, "玩家处于封停状态", false),
	ERROR_REDEEM_CODE(30031, "无效兑换码", false),
	
	
	ERROR_MAIL_CONTEXT_TOO_LONG(30032, "邮件长度问题", false),
	ERROR_MAIL_ITEM(30033, "邮件奖励错误", false),
	ERROR_NOT_BELONG_THIS_ROLE(30034, "邮件不属于这个玩家", false),
	
	ERROR_YYB_ADD_ORDER(30035, "应用宝渠道支付", false),
	
	ERROR_VIVO_ADD_ORDER(30036, "VIVO渠道支付", false),
	ERROR_HUAWEI_ADD_ORDER(30037, "华为渠道支付", false),
	ERROR_LENOVO_ADD_ORDER(30038, "联想渠道支付", false),
	
	ERROR_PAY_GOOD_PRICE_ERROR(30039,"商品价格验证错误",false),
	
	ERROR_NOT_EXISTS_CHANNEL(30040,"渠道不存在",false),
	
	ERROR_SERVER_IS_FULL(30041,"游戏服人数已满",false),
	;
	
	private int errorCode;
	private String desc;
	
	// 如果日志等级为error级,则 true 写入日志中;如果日志等级为 info ,则无论真假都写入日志中
	private boolean isWriteLog = true;
	
	private KairoErrorCode(int errorCode, String desc, boolean isWriteLog) {
        this.errorCode = errorCode;
        this.desc = desc;
        this.isWriteLog = isWriteLog;
    }
	public int getErrorCode() {
		return errorCode;
	}
	public String getDesc() {
		return desc;
	}
	public boolean isWriteLog() {
		return isWriteLog;
	}
	
	@Override
	public String toString(){
		return Integer.toString(errorCode) + " " + desc;
	}
}
