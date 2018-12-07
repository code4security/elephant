package com.sjhy.platform.persist.mysql.game;

import com.sjhy.platform.client.dto.game.ChannelAndVersion;

public interface ChannelAndVersionMapper {
    int deleteByPrimaryKey(String channelId);

    int insert(ChannelAndVersion record);

    int insertSelective(ChannelAndVersion record);

    ChannelAndVersion selectByPrimaryKey(String channelId);

    int updateByPrimaryKeySelective(ChannelAndVersion record);

    int updateByPrimaryKey(ChannelAndVersion record);
}