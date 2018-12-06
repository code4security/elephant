package com.sjhy.platform.persist.mysql;

import com.sjhy.platform.client.dto.base.ModuleSwitch;
import com.sjhy.platform.client.dto.base.ModuleSwitchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ModuleSwitchMapper {
    int countByExample(ModuleSwitchExample example);

    int deleteByExample(ModuleSwitchExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ModuleSwitch record);

    int insertSelective(ModuleSwitch record);

    List<ModuleSwitch> selectByExample(ModuleSwitchExample example);

    ModuleSwitch selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ModuleSwitch record, @Param("example") ModuleSwitchExample example);

    int updateByExample(@Param("record") ModuleSwitch record, @Param("example") ModuleSwitchExample example);

    int updateByPrimaryKeySelective(ModuleSwitch record);

    int updateByPrimaryKey(ModuleSwitch record);
}