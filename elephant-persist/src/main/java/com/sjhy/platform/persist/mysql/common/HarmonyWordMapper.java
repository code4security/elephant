package com.sjhy.platform.persist.mysql.common;

import com.sjhy.platform.client.dto.common.HarmonyWord;

public interface HarmonyWordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HarmonyWord record);

    int insertSelective(HarmonyWord record);

    HarmonyWord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HarmonyWord record);

    int updateByPrimaryKey(HarmonyWord record);
}