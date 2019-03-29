package com.sjhy.platform.client.deploy.config;
/** 
 * <p>类说明:</p>
 * <p>文件名： OperatorCodeConfig.java</p>
 * <p>创建人及时间：	宋士龙 2012-6-12</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 **/
public enum CmOpCode {
	// 0x10,登陆及创建用户相关
	CREATE_PLAYER_REQ(0x1001, "创建新用户"),
	ENTER_GAME_REQ(0x1002, "用户登录游戏"),
	MANAGER_KICK_OUT(0x1004, "管理员把玩家踢出游戏"),
	UPDATE_ROLE_NAME_REQ(0x1005, "更新玩家名"),
	RECONNECTION_REQ(0x1006, "断线重登陆游戏"),
	HEARTBEAT_REQ(0x1007, "心跳"),
	CHECK_PLAYER_NAME_REQ(0x1008, "检验用户名是否存在"),
	
	// 0x2, 操作bucket
	OSS_PUT_BUCKET_REQ(0x2001, "请求上传信息"),
	OSS_GET_BUCKET_REQ(0x2002, "请求用户bucket信息"),
	OSS_DEL_BUCKET_REQ(0x2003, "请求删除bucket信息"),
	OSS_GET_BUCKET_POLICY_REQ(0x2004, "请求操作oss key信息"),
	OSS_NOTIFY_END_REQ(0x2005, "请求操作oss key信息"),
	
	UPDATE_GAMEDATA_REQ(0x2100, "更新存档信息"),
	MD_LOG_REPORT_REQ(0x2101, "埋点"),
	
	//0x3F31,邮件
	Mail_Send_Req(0x2300, "发送邮件" ),
	Mail_Delete_Req(0x2301, "删除邮件" ),
	
	Mail_List_Req(0x2303, "获取邮件列表" ),
	Mail_Read_Req(0x2304, "读邮件" ),
	Mail_Get_New_Num_Req(0x2305, "获取新邮件数量" ),
	Mail_Get_Item_Req(0x2307, "获取邮件物品" ),
	
	// 支付消息ID
	PAY_ADD_ORDER_REQ(0x3001, "支付下订单"),
	PAY_PLAYER_GET_INFO_REQ(0x2202, "取得玩家支付"),
	PAY_ORDER_CONFIRM_NOTIFY_REQ(0x2203, "订单确认"),
	PAY_IS_BUY_REQ(0X2204, "是否已购买"),
	PAY_GETMEDAL_REQ(0X2205, "获得奖牌"),
	
	REDEEM_CODE_EXCHANGE_REQ(0X3008, "兑换码兑换"),
	
	// 0x41,获取连接列表
	Url_List_Req(0x4101, "获取连接列表" ),
	
	// 0x4F测试
	TEST_REQ(0x4F01,"测试请求"),
	
	//=====================下面为GM指令协议码=================================
	// 0x50,GM指令
	GM_KICKOUT_PLAYER_REQ(0x5001, "踢出玩家请求"),
	GM_GET_ONLINE_NUMBER_REQ(0x5002, "取得在线人数请求"),
	GM_BAN_PLAYER_REQ( 0x5003, "封停或者解除封停玩家" ),
	GM_GENERAL_USE_REQ( 0x5004, "通用GMT命令" ),
	
    // 0x51,支付通知
	PAY_NOTIFY_STATUS_REQ(0x5101, "支付状态通知请求"),
	
	// 0x60,有用游戏登录
	LOGIN_CHALLENGE_REQ(0x6001, "登录第一次握手请求"),
	LOGIN_PROOF_REQ(0x6002, "登录第二次握手"),
	CONFORM_SERVER_REQ(0x6003, "选服登录请求"),
	ACCOUNT_BIND_REQ(0x6004, "账号绑定请求"),
	ACCOUNT_UNBIND_REQ(0x6005, "账号解绑请求"),
	CHANNEL_AND_VERSION_REQ(0x6006, "玩家校验渠道号和版本号请求"),
	VERIFY_SESSION_REQ(0x6007, "验证第三方渠道Session请求"),
	GAME_NOTIFY_REQ(0x6008, "游戏公告"),
	;	
	
	
	private short opCode;
	private String desc;
	private CmOpCode(int opCode,String desc) {
        this.opCode = (short)opCode;
        this.desc = desc;
    }
    public short getOpCode() {
        return opCode;
    }
	public String getDesc() {
		return desc;
	}
}
