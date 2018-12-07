package com.sjhy.platform.persist.mysql.history;

import com.sjhy.platform.client.dto.history.PlayerLoginLogs;

public interface PlayerLoginLogsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlayerLoginLogs record);

    int insertSelective(PlayerLoginLogs record);

    PlayerLoginLogs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlayerLoginLogs record);

    int updateByPrimaryKey(PlayerLoginLogs record);
}