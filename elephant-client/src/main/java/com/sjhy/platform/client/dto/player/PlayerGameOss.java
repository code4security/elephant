package com.sjhy.platform.client.dto.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 玩家存档信息表
 * 
 * @author wcyong
 * 
 * @date 2018-12-07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerGameOss {
    /**
     * ID
     */
    private Integer id;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * oss bucket
     */
    private String bucket;

    /**
     * 存档分类
     */
    private Integer objType;

    /**
     * oss key
     */
    private String objKey;

    /**
     * 游戏金币数量
     */
    private Long gold;

    /**
     * 保存时间
     */
    private String saveTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 最后登录时间
     */
    private Date endTime;

    /**
     * 上传验证用
     */
    private String metaMd5;

    /**
     * 存档名称
     */
    private String fname;

    /**
     * 上一次存档时间
     */
    private Date backupOssTime;
}