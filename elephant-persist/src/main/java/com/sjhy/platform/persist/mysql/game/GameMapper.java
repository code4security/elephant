package com.sjhy.platform.persist.mysql.game;

import com.sjhy.platform.client.dto.game.Game;
import org.springframework.stereotype.Repository;

@Repository
public interface GameMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Game record);

    int insertSelective(Game record);

    Game selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Game record);

    int updateByPrimaryKey(Game record);

    //查询游戏id
    Game selectByGameId(String gameId);
}