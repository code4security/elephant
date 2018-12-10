package com.sjhy.platform.persist.mysql.history;

import com.sjhy.platform.client.dto.history.PlayerLipinmaLog;

public interface PlayerLipinmaLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlayerLipinmaLog record);

    int insertSelective(PlayerLipinmaLog record);

    PlayerLipinmaLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlayerLipinmaLog record);

    int updateByPrimaryKey(PlayerLipinmaLog record);
}