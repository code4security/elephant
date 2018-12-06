package com.sjhy.platform.persist.mysql;

import com.sjhy.platform.client.dto.base.HarmonyWord;
import com.sjhy.platform.client.dto.base.HarmonyWordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HarmonyWordMapper {
    int countByExample(HarmonyWordExample example);

    int deleteByExample(HarmonyWordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HarmonyWord record);

    int insertSelective(HarmonyWord record);

    List<HarmonyWord> selectByExample(HarmonyWordExample example);

    HarmonyWord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HarmonyWord record, @Param("example") HarmonyWordExample example);

    int updateByExample(@Param("record") HarmonyWord record, @Param("example") HarmonyWordExample example);

    int updateByPrimaryKeySelective(HarmonyWord record);

    int updateByPrimaryKey(HarmonyWord record);
}