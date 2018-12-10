package com.sjhy.platform.persist.mysql.common;

import com.sjhy.platform.client.dto.common.IpLocation;

public interface IpLocationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IpLocation record);

    int insertSelective(IpLocation record);

    IpLocation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IpLocation record);

    int updateByPrimaryKey(IpLocation record);
}