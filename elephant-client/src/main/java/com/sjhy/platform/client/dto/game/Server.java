package com.sjhy.platform.client.dto.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务器信息表
 * 
 * @author HJ
 * 
 * @date 2018-12-10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Server {
    /**
     * ID
     */
    private Integer id;

    /**
     * 服务器id
     */
    private Integer serverId;

    /**
     * 游戏id
     */
    private String gameId;

    /**
     * 语言
     */
    private String language;

    /**
     * 名字
     */
    private String name;

    /**
     * 是否可用
     */
    private Boolean isAvailable;

    /**
     * 是否推荐
     */
    private Short recommend;

    /**
     * 服务器ip地址（外网）
     */
    private String ip;

    /**
     * 服务器ip地址（内网）
     */
    private String ipInternal;

    /**
     * 端口号
     */
    private Integer portNumber;

    private String startUrl;

    /**
     * 版本号
     */
    private String versionNum;
}