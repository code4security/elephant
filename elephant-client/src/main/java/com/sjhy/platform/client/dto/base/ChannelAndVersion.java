package com.sjhy.platform.client.dto.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 渠道和版本信息表
 * 
 * @author HJ
 * 
 * @date 2018-12-06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChannelAndVersion {
    /**
     * 渠道id
     */
    private String channelId;

    /**
     * 是否可用
     */
    private Boolean isAvailable;

    /**
     * 渠道全称
     */
    private String channelInfo;

    /**
     * 版本号
     */
    private String versionNum;

    /**
     * 版本更新路径
     */
    private String versionDownload;

}