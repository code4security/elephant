package com.sjhy.platform.persist.mysql.player;

import com.sjhy.platform.client.dto.player.PlayerBanList;

public interface PlayerBanListMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(PlayerBanList record);

    int insertSelective(PlayerBanList record);

    PlayerBanList selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(PlayerBanList record);

    int updateByPrimaryKey(PlayerBanList record);
}