package com.sjhy.platform.persist.mysql.player;

import com.sjhy.platform.client.dto.player.PlayerChannel;

public interface PlayerChannelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlayerChannel record);

    int insertSelective(PlayerChannel record);

    PlayerChannel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlayerChannel record);

    int updateByPrimaryKey(PlayerChannel record);

    //查询玩家渠道id
    PlayerChannel selectByChannelUserId(PlayerChannel record);
}