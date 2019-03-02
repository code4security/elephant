package com.sjhy.platform.persist.mysql.player;

import com.sjhy.platform.client.dto.player.PlayerGameOss;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlayerGameOssMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlayerGameOss record);

    int insertSelective(PlayerGameOss record);

    PlayerGameOss selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlayerGameOss record);

    int updateByPrimaryKey(PlayerGameOss record);

    // 查询oss文件id
    PlayerGameOss selectByGameKey(@Param("id") int id, @Param("gameId") String gameId);

    // 根据role_id 查询存档
    List<PlayerGameOss> selectByRoleId(@Param("gameId") String gameId, @Param("roleId") long roleId);

    // 修改最后时间
    int updateEndtimeByRoleId(@Param("gameId") String gameId, @Param("roleId") long roleId);

    // 查询objKey
    PlayerGameOss selectByRoleIdAndObjKey(PlayerGameOss record);
}