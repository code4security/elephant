package com.sjhy.platform.persist.mysql.history;

import com.sjhy.platform.client.dto.history.PlayerLoginLog;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerLoginLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlayerLoginLog record);

    int insertSelective(PlayerLoginLog record);

    PlayerLoginLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlayerLoginLog record);

    int updateByPrimaryKey(PlayerLoginLog record);
}