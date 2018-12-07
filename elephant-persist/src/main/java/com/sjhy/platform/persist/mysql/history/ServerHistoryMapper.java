package com.sjhy.platform.persist.mysql.history;

import com.sjhy.platform.client.dto.history.ServerHistory;

public interface ServerHistoryMapper {
    int deleteByPrimaryKey(Long playerId);

    int insert(ServerHistory record);

    int insertSelective(ServerHistory record);

    ServerHistory selectByPrimaryKey(Long playerId);

    int updateByPrimaryKeySelective(ServerHistory record);

    int updateByPrimaryKey(ServerHistory record);
}