package com.sjhy.platform.persist.mysql.game;

import com.sjhy.platform.client.dto.game.GameConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GameConfig record);

    int insertSelective(GameConfig record);

    GameConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GameConfig record);

    int updateByPrimaryKey(GameConfig record);

    GameConfig selectByCon(@Param("gameId") String gameId, @Param("channelId") String channelId);
}