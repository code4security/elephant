package com.sjhy.platform.persist.mysql.game;

import com.sjhy.platform.client.dto.game.ChannelAndVersion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelAndVersionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChannelAndVersion record);

    int insertSelective(ChannelAndVersion record);

    ChannelAndVersion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChannelAndVersion record);

    int updateByPrimaryKey(ChannelAndVersion record);

    // 验证渠道
    ChannelAndVersion verifyChannel(ChannelAndVersion record);

    // 查询渠道id，通过英文简称
    String selectByChannelId(@Param("gameId") String gameId, @Param("channelInfo") String channelInfo);
}