package com.sjhy.platform.persist.mysql.player;

import com.sjhy.platform.client.dto.player.Player;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Player record);

    int insertSelective(Player record);

    Player selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Player record);

    int updateByPrimaryKey(Player record);

    //查询玩家id
    Player selectByPlayerId(Player record);

    // 查询所有玩家
    int selectByCount(Player record);
}