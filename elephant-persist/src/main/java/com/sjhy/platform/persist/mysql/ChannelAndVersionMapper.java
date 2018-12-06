package com.sjhy.platform.persist.mysql;

import com.sjhy.platform.client.dto.base.ChannelAndVersion;
import com.sjhy.platform.client.dto.base.ChannelAndVersionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChannelAndVersionMapper {
    int countByExample(ChannelAndVersionExample example);

    int deleteByExample(ChannelAndVersionExample example);

    int deleteByPrimaryKey(String channelId);

    int insert(ChannelAndVersion record);

    int insertSelective(ChannelAndVersion record);

    List<ChannelAndVersion> selectByExample(ChannelAndVersionExample example);

    ChannelAndVersion selectByPrimaryKey(String channelId);

    int updateByExampleSelective(@Param("record") ChannelAndVersion record, @Param("example") ChannelAndVersionExample example);

    int updateByExample(@Param("record") ChannelAndVersion record, @Param("example") ChannelAndVersionExample example);

    int updateByPrimaryKeySelective(ChannelAndVersion record);

    int updateByPrimaryKey(ChannelAndVersion record);
}