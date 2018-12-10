package com.sjhy.platform.persist.mysql.player;

import com.sjhy.platform.client.dto.player.PlayerBanList;

public interface PlayerBanListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlayerBanList record);

    int insertSelective(PlayerBanList record);

    PlayerBanList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlayerBanList record);

    int updateByPrimaryKey(PlayerBanList record);
}