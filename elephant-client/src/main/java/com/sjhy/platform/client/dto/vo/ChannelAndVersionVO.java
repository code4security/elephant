package com.sjhy.platform.client.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 玩家校验渠道号和版本号之后的返回值
 * @author HJ
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelAndVersionVO implements Serializable {
	private boolean isPass = false; // 是否通过验证(true,不需要升级;false,需要升级)
	private boolean isEnforceUpgrade = false; // 是否必须升级(预留字段)
	private String versionDownload = ""; // 当前版本下载地址
}
