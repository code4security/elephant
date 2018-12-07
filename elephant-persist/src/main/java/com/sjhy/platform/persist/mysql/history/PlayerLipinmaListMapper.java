package com.sjhy.platform.persist.mysql.history;

import com.sjhy.platform.client.dto.history.PlayerLipinmaList;

public interface PlayerLipinmaListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlayerLipinmaList record);

    int insertSelective(PlayerLipinmaList record);

    PlayerLipinmaList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlayerLipinmaList record);

    int updateByPrimaryKey(PlayerLipinmaList record);
}