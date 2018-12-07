package com.sjhy.platform.client.dto.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 邮件表
 * 
 * @author wcyong
 * 
 * @date 2018-12-07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mail {
    /**
     * 邮件id
     */
    private Long mailId;

    /**
     * 发送者id
     */
    private Long sendRoleId;

    /**
     * 接收者id
     */
    private Long recvRoleId;

    /**
     * 游戏id
     */
    private Integer gameId;

    /**
     * 邮件类型
     */
    private Short type;

    /**
     * 邮件标题
     */
    private String title;

    /**
     * 邮件内容
     */
    private String context;

    /**
     * 物品id
     */
    private Integer goodsId;

    /**
     * 物品数量
     */
    private Integer goodsNum;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 邮件状态（未领，已领）
     */
    private Boolean status;
}