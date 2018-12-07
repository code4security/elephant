package com.sjhy.platform.persist.mysql.common;

import com.sjhy.platform.client.dto.common.VirtualCurrency;

public interface VirtualCurrencyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VirtualCurrency record);

    int insertSelective(VirtualCurrency record);

    VirtualCurrency selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VirtualCurrency record);

    int updateByPrimaryKey(VirtualCurrency record);
}