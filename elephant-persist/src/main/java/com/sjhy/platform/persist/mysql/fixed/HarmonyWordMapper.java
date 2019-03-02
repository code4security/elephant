package com.sjhy.platform.persist.mysql.fixed;

import com.sjhy.platform.client.dto.fixed.HarmonyWord;

import java.util.List;

public interface HarmonyWordMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(HarmonyWord record);

    int insertSelective(HarmonyWord record);

    HarmonyWord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HarmonyWord record);

    int updateByPrimaryKey(HarmonyWord record);

    // 查询全部
    List<HarmonyWord> selectByAll();
}