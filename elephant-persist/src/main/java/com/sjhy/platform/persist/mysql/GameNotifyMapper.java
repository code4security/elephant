package com.sjhy.platform.persist.mysql;

import com.sjhy.platform.client.dto.base.GameNotify;
import com.sjhy.platform.client.dto.base.GameNotifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GameNotifyMapper {
    int countByExample(GameNotifyExample example);

    int deleteByExample(GameNotifyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GameNotify record);

    int insertSelective(GameNotify record);

    List<GameNotify> selectByExample(GameNotifyExample example);

    GameNotify selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GameNotify record, @Param("example") GameNotifyExample example);

    int updateByExample(@Param("record") GameNotify record, @Param("example") GameNotifyExample example);

    int updateByPrimaryKeySelective(GameNotify record);

    int updateByPrimaryKey(GameNotify record);
}