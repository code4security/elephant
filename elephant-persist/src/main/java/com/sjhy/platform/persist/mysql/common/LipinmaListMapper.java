package com.sjhy.platform.persist.mysql.common;

import com.sjhy.platform.client.dto.common.LipinmaList;

public interface LipinmaListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LipinmaList record);

    int insertSelective(LipinmaList record);

    LipinmaList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LipinmaList record);

    int updateByPrimaryKey(LipinmaList record);
}