package com.sjhy.platform.persist.mysql.player;

import com.sjhy.platform.client.dto.player.PlayerRole;

public interface PlayerRoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(PlayerRole record);

    int insertSelective(PlayerRole record);

    PlayerRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(PlayerRole record);

    int updateByPrimaryKey(PlayerRole record);
}