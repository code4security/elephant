package com.sjhy.platform.persist.mysql.fixed;

import com.sjhy.platform.client.dto.fixed.IpLocation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IpLocationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IpLocation record);

    int insertSelective(IpLocation record);

    IpLocation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IpLocation record);

    int updateByPrimaryKey(IpLocation record);

    //查询ip区域
    List<IpLocation> selectByStart(@Param("startIp") Long startIp);
}