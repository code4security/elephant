package com.sjhy.platform.persist.mysql.player;

import com.sjhy.platform.client.dto.player.Player;

public interface PlayerMapper {
    int deleteByPrimaryKey(Long playerId);

    int insert(Player record);

    int insertSelective(Player record);

    Player selectByPrimaryKey(Long playerId);

    int updateByPrimaryKeySelective(Player record);

    int updateByPrimaryKey(Player record);
}