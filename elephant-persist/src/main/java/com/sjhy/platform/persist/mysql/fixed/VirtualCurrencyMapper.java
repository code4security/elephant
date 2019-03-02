package com.sjhy.platform.persist.mysql.fixed;

import com.sjhy.platform.client.dto.fixed.VirtualCurrency;
import org.apache.ibatis.annotations.Param;

public interface VirtualCurrencyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VirtualCurrency record);

    int insertSelective(VirtualCurrency record);

    VirtualCurrency selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VirtualCurrency record);

    int updateByPrimaryKey(VirtualCurrency record);

    // 查询货币是否存在
    VirtualCurrency selectByUnit(@Param("unit") String unit);
}