package com.sjhy.platform.client.dto.base;

import lombok.Data;

/**
 * @HJ
 */
@Data
public class GameServerVO {
    private int id;
    private String name;
    private String ip;
    private int port;
    private int loginState; // 服务器登陆状态，1为当前登录，2为曾经登录，3为从未登录
    private int recommendState; // 是否推荐，1为不推荐，2为推荐
}
