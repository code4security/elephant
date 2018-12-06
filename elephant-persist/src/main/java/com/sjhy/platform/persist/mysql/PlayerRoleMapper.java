package com.sjhy.platform.persist.mysql;

import com.sjhy.platform.client.dto.base.PlayerRole;
import com.sjhy.platform.client.dto.base.PlayerRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlayerRoleMapper {
    int countByExample(PlayerRoleExample example);

    int deleteByExample(PlayerRoleExample example);

    int deleteByPrimaryKey(Long roleId);

    int insert(PlayerRole record);

    int insertSelective(PlayerRole record);

    List<PlayerRole> selectByExample(PlayerRoleExample example);

    PlayerRole selectByPrimaryKey(Long roleId);

    int updateByExampleSelective(@Param("record") PlayerRole record, @Param("example") PlayerRoleExample example);

    int updateByExample(@Param("record") PlayerRole record, @Param("example") PlayerRoleExample example);

    int updateByPrimaryKeySelective(PlayerRole record);

    int updateByPrimaryKey(PlayerRole record);
}