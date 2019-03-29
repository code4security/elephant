package com.sjhy.platform.client.dto.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 游戏角色信息表
 * 
 * @author HJ
 * 
 * @date 2018-12-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerRole implements Comparable,Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 玩家id
     */
    private Long playerId;

    /**
     * 角色名字
     */
    private String roleName;

    /**
     * 游戏id
     */
    private String gameId;

    /**
     * 渠道id
     */
    private String channelId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后登陆时间
     */
    private Date lastLoginTime;

    /**
     * 最后登陆服务器
     */
    private Integer lastLoginServer;

    /**
     * 好友id
     */
    private Integer friendid;

    /**
     * 国家
     */
    private String country;

    /**
     * 在线时间
     */
    private Long minute;

    /**
     * 更新时间
     */
    private Date updated;

    /**
     * 添加好友数量
     */
    private Byte regFriend;

    /**
     * 删除好友数量
     */
    private Byte isDeleted;

    /**
     * 去广告
     */
    private Date adtime;

    /**
     * 黄金会员
     */
    private Date viptime;

    /**
     * 虚拟货币数量
     */
    private Integer currency;

    @Override
    public int compareTo(Object o) {
        PlayerRole role = (PlayerRole) o;
        if (this.id-role.id>0){
            return 1;
        }else if (this.id-role.id==0){
            return 0;
        }
        return -1;
    }
}