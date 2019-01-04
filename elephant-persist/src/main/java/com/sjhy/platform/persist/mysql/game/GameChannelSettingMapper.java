package com.sjhy.platform.persist.mysql.game;

import com.sjhy.platform.client.dto.game.GameChannelSetting;
import org.apache.ibatis.annotations.Param;

public interface GameChannelSettingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GameChannelSetting record);

    int insertSelective(GameChannelSetting record);

    GameChannelSetting selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GameChannelSetting record);

    int updateByPrimaryKey(GameChannelSetting record);

    // 查询子渠道
    int selectBySubId(@Param("gameId") String gameId, @Param("channelId") String channelId);

    // 验证子渠道
    GameChannelSetting verifySubId(@Param("gameId") String gameId, @Param("channelId") String channelId, @Param("subChannelId") String subChannelId);
}