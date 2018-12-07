package com.sjhy.platform.persist.mysql.history;

import com.sjhy.platform.client.dto.history.PlayerPayLog;

public interface PlayerPayLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlayerPayLog record);

    int insertSelective(PlayerPayLog record);

    PlayerPayLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlayerPayLog record);

    int updateByPrimaryKey(PlayerPayLog record);
}