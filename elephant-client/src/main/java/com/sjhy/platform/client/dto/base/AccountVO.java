package com.sjhy.platform.client.dto.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @HJ
 */
@Data
public class AccountVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String deviceUniquelyCode;

    private String name;

    private String password;

    private Integer serverId;

    private Integer activationState = 0;// 默认不需要激活码

    private String chUserId;

    private String channelId;

    private int gameId;
}
