package com.sjhy.platform.persist.mysql.player;

import com.sjhy.platform.client.dto.player.PlayerRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlayerRole record);

    int insertSelective(PlayerRole record);

    PlayerRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlayerRole record);

    int updateByPrimaryKey(PlayerRole record);

    // 根据playerId查询玩家是否存在
    PlayerRole selectByPlayerId(@Param("gameId") String gameId,@Param("playerId") Long playerId);

    int countByRole(PlayerRole record);

    // 根据roleId查询玩家是否存在
    PlayerRole selectByRoleId(@Param("gameId") String gameId, @Param("roleId") Long roleId);

    // 根据roleName查询玩家是否存在
    List<PlayerRole> selectByRoleName(@Param("gameId") String gameId, @Param("roleName") String roleName);
}