package com.sjhy.platform.biz.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 玩家校验渠道号和版本号之后的返回值
 * @author HJ
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelAndVersionVO {
	private boolean isPass = false; // 是否通过验证(true,不需要升级;false,需要升级)
	private boolean isEnforceUpgrade = false; // 是否必须升级(预留字段)
	private String versionDownload = ""; // 当前版本下载地址
}
