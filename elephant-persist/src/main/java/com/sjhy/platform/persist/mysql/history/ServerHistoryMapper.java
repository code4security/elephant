package com.sjhy.platform.persist.mysql.history;

import com.sjhy.platform.client.dto.history.ServerHistory;

public interface ServerHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ServerHistory record);

    int insertSelective(ServerHistory record);

    ServerHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ServerHistory record);

    int updateByPrimaryKey(ServerHistory record);

    //查询玩家登陆服务器记录
    ServerHistory selectByHistory(ServerHistory record);
}