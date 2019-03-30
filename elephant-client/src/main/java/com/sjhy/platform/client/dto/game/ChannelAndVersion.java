package com.sjhy.platform.client.dto.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 渠道和版本信息表
 * 
 * @author HJ
 * 
 * @date 2018-12-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChannelAndVersion implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 渠道id
     */
    private String channelId;

    /**
     * 游戏id
     */
    private String gameId;

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