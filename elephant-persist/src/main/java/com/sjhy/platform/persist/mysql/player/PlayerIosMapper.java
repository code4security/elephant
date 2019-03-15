package com.sjhy.platform.persist.mysql.player;

import com.sjhy.platform.client.dto.player.PlayerIos;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerIosMapper {
    int deleteByPrimaryKey(Long iosId);

    int insert(PlayerIos record);

    int insertSelective(PlayerIos record);

    PlayerIos selectByPrimaryKey(Long iosId);

    int updateByPrimaryKeySelective(PlayerIos record);

    int updateByPrimaryKey(PlayerIos record);

    // 查询玩家是否存在
    PlayerIos selectByClientId(PlayerIos record);

    // 查询玩家是否存在
    PlayerIos selectByGameId(PlayerIos record);
}