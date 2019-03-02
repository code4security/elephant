package com.sjhy.platform.persist.mysql.game;

import com.sjhy.platform.client.dto.game.ModuleSwitch;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleSwitchMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ModuleSwitch record);

    int insertSelective(ModuleSwitch record);

    ModuleSwitch selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ModuleSwitch record);

    int updateByPrimaryKey(ModuleSwitch record);

    //查询游戏模块开关
    ModuleSwitch selectByModule(ModuleSwitch record);
}