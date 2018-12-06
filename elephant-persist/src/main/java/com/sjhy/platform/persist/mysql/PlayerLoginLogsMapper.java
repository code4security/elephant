package com.sjhy.platform.persist.mysql;

import com.sjhy.platform.client.dto.base.PlayerLoginLogs;
import com.sjhy.platform.client.dto.base.PlayerLoginLogsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlayerLoginLogsMapper {
    int countByExample(PlayerLoginLogsExample example);

    int deleteByExample(PlayerLoginLogsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PlayerLoginLogs record);

    int insertSelective(PlayerLoginLogs record);

    List<PlayerLoginLogs> selectByExample(PlayerLoginLogsExample example);

    PlayerLoginLogs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PlayerLoginLogs record, @Param("example") PlayerLoginLogsExample example);

    int updateByExample(@Param("record") PlayerLoginLogs record, @Param("example") PlayerLoginLogsExample example);

    int updateByPrimaryKeySelective(PlayerLoginLogs record);

    int updateByPrimaryKey(PlayerLoginLogs record);
}