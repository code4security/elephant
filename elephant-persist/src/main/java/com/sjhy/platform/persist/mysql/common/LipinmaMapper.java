package com.sjhy.platform.persist.mysql.common;

import com.sjhy.platform.client.dto.common.Lipinma;

public interface LipinmaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Lipinma record);

    int insertSelective(Lipinma record);

    Lipinma selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Lipinma record);

    int updateByPrimaryKey(Lipinma record);
}