package com.sjhy.platform.persist.mysql.game;

import com.sjhy.platform.client.dto.game.ChannelPayParameter;
import org.apache.ibatis.annotations.Param;

public interface ChannelPayParameterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChannelPayParameter record);

    int insertSelective(ChannelPayParameter record);

    ChannelPayParameter selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChannelPayParameter record);

    int updateByPrimaryKey(ChannelPayParameter record);

    // 查询子渠道
    int selectBySubId(@Param("gameId") String gameId, @Param("channelId") String channelId);
}