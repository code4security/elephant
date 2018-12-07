package com.sjhy.platform.client.dto.game;

/**
 * 游戏名单信息表
 * 
 * @author wcyong
 * 
 * @date 2018-12-07
 */
public class Game {
    /**
     * ID
     */
    private Integer id;

    /**
     * 中文名
     */
    private String name;

    /**
     * 英文名
     */
    private String nameEn;

    /**
     * 类型（单机、网游）
     */
    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn == null ? null : nameEn.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}