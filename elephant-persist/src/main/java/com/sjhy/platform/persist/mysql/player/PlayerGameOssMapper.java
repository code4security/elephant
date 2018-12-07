package com.sjhy.platform.persist.mysql.player;

import com.sjhy.platform.client.dto.player.PlayerGameOss;

public interface PlayerGameOssMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlayerGameOss record);

    int insertSelective(PlayerGameOss record);

    PlayerGameOss selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlayerGameOss record);

    int updateByPrimaryKey(PlayerGameOss record);
}