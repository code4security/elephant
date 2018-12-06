package com.sjhy.platform.persist.mysql;

import com.sjhy.platform.client.dto.base.ServerHistory;
import com.sjhy.platform.client.dto.base.ServerHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ServerHistoryMapper {
    int countByExample(ServerHistoryExample example);

    int deleteByExample(ServerHistoryExample example);

    int deleteByPrimaryKey(Long playerId);

    int insert(ServerHistory record);

    int insertSelective(ServerHistory record);

    List<ServerHistory> selectByExample(ServerHistoryExample example);

    ServerHistory selectByPrimaryKey(Long playerId);

    int updateByExampleSelective(@Param("record") ServerHistory record, @Param("example") ServerHistoryExample example);

    int updateByExample(@Param("record") ServerHistory record, @Param("example") ServerHistoryExample example);

    int updateByPrimaryKeySelective(ServerHistory record);

    int updateByPrimaryKey(ServerHistory record);
}