package com.sjhy.platform.biz.config;
/** 
 * <p>类说明:</p>
 * <p>文件名： GamePayConfig.java</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 **/
public class GamePayConfig {

	public static String ACTION_ADD_ORDER="addOrder";
	public static String ACTION_CONFIRM_RECEIVE_NOTIFY="confirmReceiveNotify";
	public static String ACTION_GET_PAY_GOODS="getPayGoods";
	public static String ACTION_GET_PAY_INFO="getPayInfo";
	public static String ACTION_GET_PAY_STATUS="getPayStatus";
	public static String ACTION_GET_kairo_PAY_STATUS="getkairoPayStatus";
	public static String ACTION_GET_USER_PAY_INFO="getUserPayInfo";

	// QQ游戏下订单
	public static String ACTION_ADD_YYB_ORDER="addYYBOrder";
	public static String ACTION_ADD_360_ORDER="addQihooOrder";
	public static String ACTION_ADD_VIVO_ORDER="addVivoOrder";
	public static String ACTION_ADD_JINLI_ORDER="addJinliOrder";
	public static String ACTION_ADD_LENOVO_ORDER="addLenovoOrder";
	public static String ACTION_ADD_TX_ORDER="addTxOrder";
	
	public static final int ERROR_ACTION_ISNULL=-1001;
	public static final int ERROR_ACTION_UNEXIST=-1002;
	public static final int ERROR_DATA_ISNULL=-1003;
	public static final int ERROR_JSON_FORMAT=-1004;
	public static final int ERROR_JSON_MAPPER=-1005;
	public static final int ERROR_KEY_ISNULL=-1006;
	public static final int ERROR_KEY_UNEXIST=-1007;
	public static final int ERROR_SIGN_ISNULL=-1008;
	public static final int ERROR_SIGN_ERROR=-1009;
	
	public static final int SUCCESS=1;
	public static final int ERROR_UNKNOW=0;
	
	public static final int ERROR_UID_ISNULL=-1;
	public static final int ERROR_UID_ERROR=-2;
	public static final int ERROR_GOOD_ID_ISNULL=-3;
	public static final int ERROR_GOOD_ID_ERROR=-4;
	public static final int ERROR_GOOD_NUMBER_ISNULL=-5;
	public static final int ERROR_ORDER_ID_ISNULL=-6;
	public static final int ERROR_ORDER_ID_ERROR=-7;
	public static final int ERROR_NO_PAY_INFO=-8;
	public static final int ERROR_NO_GOOD_LIST=-9;
	public static final int ERROR_PAY_POINT_ISNULL=-10;
	public static final int ERROR_CHANNEL_ISNULL=-11;
	public static final int ERROR_CHANNEL_ERROR=-12;
}
