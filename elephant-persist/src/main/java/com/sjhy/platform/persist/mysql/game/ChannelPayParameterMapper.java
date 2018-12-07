package com.sjhy.platform.persist.mysql.game;

import com.sjhy.platform.client.dto.game.ChannelPayParameter;

public interface ChannelPayParameterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChannelPayParameter record);

    int insertSelective(ChannelPayParameter record);

    ChannelPayParameter selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChannelPayParameter record);

    int updateByPrimaryKey(ChannelPayParameter record);
}