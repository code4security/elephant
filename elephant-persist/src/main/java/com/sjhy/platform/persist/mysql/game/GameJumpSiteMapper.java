package com.sjhy.platform.persist.mysql.game;

import com.sjhy.platform.client.dto.game.GameJumpSite;

public interface GameJumpSiteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GameJumpSite record);

    int insertSelective(GameJumpSite record);

    GameJumpSite selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GameJumpSite record);

    int updateByPrimaryKey(GameJumpSite record);
}