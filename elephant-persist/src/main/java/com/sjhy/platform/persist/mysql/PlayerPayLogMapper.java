package com.sjhy.platform.persist.mysql;

import com.sjhy.platform.client.dto.base.PlayerPayLog;
import com.sjhy.platform.client.dto.base.PlayerPayLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlayerPayLogMapper {
    int countByExample(PlayerPayLogExample example);

    int deleteByExample(PlayerPayLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PlayerPayLog record);

    int insertSelective(PlayerPayLog record);

    List<PlayerPayLog> selectByExample(PlayerPayLogExample example);

    PlayerPayLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PlayerPayLog record, @Param("example") PlayerPayLogExample example);

    int updateByExample(@Param("record") PlayerPayLog record, @Param("example") PlayerPayLogExample example);

    int updateByPrimaryKeySelective(PlayerPayLog record);

    int updateByPrimaryKey(PlayerPayLog record);
}