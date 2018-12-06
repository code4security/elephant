package com.sjhy.platform.persist.mysql;

import com.sjhy.platform.client.dto.base.PlayerGameOss;
import com.sjhy.platform.client.dto.base.PlayerGameOssExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlayerGameOssMapper {
    int countByExample(PlayerGameOssExample example);

    int deleteByExample(PlayerGameOssExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PlayerGameOss record);

    int insertSelective(PlayerGameOss record);

    List<PlayerGameOss> selectByExample(PlayerGameOssExample example);

    PlayerGameOss selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PlayerGameOss record, @Param("example") PlayerGameOssExample example);

    int updateByExample(@Param("record") PlayerGameOss record, @Param("example") PlayerGameOssExample example);

    int updateByPrimaryKeySelective(PlayerGameOss record);

    int updateByPrimaryKey(PlayerGameOss record);
}