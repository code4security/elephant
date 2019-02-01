package com.sjhy.platform.persist.mysql.player;

import com.sjhy.platform.client.dto.player.PlayerChannel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerChannelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlayerChannel record);

    int insertSelective(PlayerChannel record);

    PlayerChannel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlayerChannel record);

    int updateByPrimaryKey(PlayerChannel record);

    //查询玩家渠道id
    PlayerChannel selectByPlayerChannel(PlayerChannel record);

    // 查询渠道用户id
    String selectByChannelUserId(@Param("gameId") String gameId, @Param("channelId") String channelId, @Param("playerId") Long playerId);
}