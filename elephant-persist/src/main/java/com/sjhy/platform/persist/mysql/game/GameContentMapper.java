package com.sjhy.platform.persist.mysql.game;

import com.sjhy.platform.client.dto.game.GameContent;
import org.springframework.stereotype.Repository;

@Repository
public interface GameContentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GameContent record);

    int insertSelective(GameContent record);

    GameContent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GameContent record);

    int updateByPrimaryKey(GameContent record);

    //查询玩家奖牌记录
    GameContent selectByRole(GameContent record);
}