package com.sjhy.platform.persist.mysql;

import com.sjhy.platform.client.dto.base.IpLocation;
import com.sjhy.platform.client.dto.base.IpLocationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IpLocationMapper {
    int countByExample(IpLocationExample example);

    int deleteByExample(IpLocationExample example);

    int deleteByPrimaryKey(Long startIpNum);

    int insert(IpLocation record);

    int insertSelective(IpLocation record);

    List<IpLocation> selectByExample(IpLocationExample example);

    IpLocation selectByPrimaryKey(Long startIpNum);

    int updateByExampleSelective(@Param("record") IpLocation record, @Param("example") IpLocationExample example);

    int updateByExample(@Param("record") IpLocation record, @Param("example") IpLocationExample example);

    int updateByPrimaryKeySelective(IpLocation record);

    int updateByPrimaryKey(IpLocation record);
}