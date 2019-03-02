package com.sjhy.platform.persist.mysql.game;

import com.sjhy.platform.client.dto.game.GiftCodeList;

public interface GiftCodeListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GiftCodeList record);

    int insertSelective(GiftCodeList record);

    GiftCodeList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GiftCodeList record);

    int updateByPrimaryKey(GiftCodeList record);
}