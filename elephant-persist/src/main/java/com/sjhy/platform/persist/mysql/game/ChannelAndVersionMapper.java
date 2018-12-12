package com.sjhy.platform.persist.mysql.game;

import com.sjhy.platform.client.dto.game.ChannelAndVersion;

public interface ChannelAndVersionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChannelAndVersion record);

    int insertSelective(ChannelAndVersion record);

    ChannelAndVersion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChannelAndVersion record);

    int updateByPrimaryKey(ChannelAndVersion record);

    //验证渠道
    ChannelAndVersion verifyChannel(ChannelAndVersion record);
}