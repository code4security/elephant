package com.sjhy.platform.persist.mysql.player;

import com.sjhy.platform.client.dto.player.PlayerGameOss;
import org.apache.ibatis.annotations.Param;

public interface PlayerGameOssMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlayerGameOss record);

    int insertSelective(PlayerGameOss record);

    PlayerGameOss selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlayerGameOss record);

    int updateByPrimaryKey(PlayerGameOss record);

    // 查询oss文件id
    PlayerGameOss selectByGameKey(@Param("id") int id, @Param("gameId") String gameId);
}