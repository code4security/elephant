package com.sjhy.platform.persist.mysql;

import com.sjhy.platform.client.dto.base.PlayChannel;
import com.sjhy.platform.client.dto.base.PlayChannelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlayChannelMapper {
    int countByExample(PlayChannelExample example);

    int deleteByExample(PlayChannelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PlayChannel record);

    int insertSelective(PlayChannel record);

    List<PlayChannel> selectByExample(PlayChannelExample example);

    PlayChannel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PlayChannel record, @Param("example") PlayChannelExample example);

    int updateByExample(@Param("record") PlayChannel record, @Param("example") PlayChannelExample example);

    int updateByPrimaryKeySelective(PlayChannel record);

    int updateByPrimaryKey(PlayChannel record);
}