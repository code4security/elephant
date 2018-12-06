package com.sjhy.platform.persist.mysql;

import com.sjhy.platform.client.dto.base.ChannelPayParameter;
import com.sjhy.platform.client.dto.base.ChannelPayParameterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChannelPayParameterMapper {
    int countByExample(ChannelPayParameterExample example);

    int deleteByExample(ChannelPayParameterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ChannelPayParameter record);

    int insertSelective(ChannelPayParameter record);

    List<ChannelPayParameter> selectByExample(ChannelPayParameterExample example);

    ChannelPayParameter selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ChannelPayParameter record, @Param("example") ChannelPayParameterExample example);

    int updateByExample(@Param("record") ChannelPayParameter record, @Param("example") ChannelPayParameterExample example);

    int updateByPrimaryKeySelective(ChannelPayParameter record);

    int updateByPrimaryKey(ChannelPayParameter record);
}