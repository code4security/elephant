package com.sjhy.platform.persist.mysql.game;

import com.sjhy.platform.client.dto.game.Lipinma;

public interface LipinmaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Lipinma record);

    int insertSelective(Lipinma record);

    Lipinma selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Lipinma record);

    int updateByPrimaryKey(Lipinma record);
}