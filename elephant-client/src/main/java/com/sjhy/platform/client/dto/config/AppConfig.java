package com.sjhy.platform.client.dto.config;
/** 
 * <p>类说明:</p>
 * <p>文件名： AppConfig.java</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 **/
public class AppConfig {
	public static final String GAME_ID="game.id";
	public static final String SERVER_ID="server.id";
	public static final String GM_SECRET_KEY="gm.secret.key";

	public static final String GAME_PAY_URL="game.pay.url";
	public static final String GAME_PAY_URL_YYB="game.pay.url.yyb";
	public static final String GAME_PAY_KEY="game.pay.key";
	
	// 服务器日志
	public static final String SERVER_LOG_PATH = "server.log.path";
	public static final String SERVER_BASEGAMEDATA_PATH="server.gamedata.path";
	
	public static final String SERVER_TOTAL_PLAYERS = "server.total.players";
	public static final String SERVER_CLOSE_ENTER_TIME = "server.close.enter.time";
	// 热更新服务器版本
	public static final String SERVER_HOT_UPGRADE_VERSION = "server.hot.upgrade.version";

	// 语言
	public static final String SERVER_LANGUAGE = "server.language";
	
	// 生产上线开关
	public static final String SERVER_DEBUG = "server.debug";
			
	public static final String ALI_OSS_BUCKET = "ali.oss.bucket";
	public static final String ALI_OSS_BUCKET_BACK = "ali.oss.bucket.back";
	
	public static final String ALI_OSS_ENDPOINT = "ali.oss.endpoint";
	public static final String ALI_OSS_ENDPOINT_INTERNAL = "ali.oss.endpoint.internal";
	
	public static final String ALI_OSS_RAM_ACCESS_KEY = "ali.oss.ram.access.key";
	public static final String ALI_OSS_RAM_ACCESS_SECRET = "ali.oss.ram.access.secret";
	
	public static final String ALI_OSS_ACCESS_KEY = "ali.oss.access.key";
	public static final String ALI_OSS_ACCESS_SECRET = "ali.oss.access.secret";
	
	public static final String ALI_OSS_ROLE_NAME = "ali.oss.role.name";
	public static final String ALI_OSS_ROLE_READ = "ali.oss.role.read";
	public static final String ALI_OSS_ROLE_WRITE = "ali.oss.role.write";
	
	public static final String ALI_OSS_REGION = "ali.oss.region";
	public static final String ALI_OSS_DURATION = "ali.oss.duration";
	
	public static final String ALI_OSS_BACKUP_TIME = "ali.oss.backup.time";
}
