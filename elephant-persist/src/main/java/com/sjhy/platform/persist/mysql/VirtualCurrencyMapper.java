package com.sjhy.platform.persist.mysql;

import com.sjhy.platform.client.dto.base.VirtualCurrency;
import com.sjhy.platform.client.dto.base.VirtualCurrencyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VirtualCurrencyMapper {
    int countByExample(VirtualCurrencyExample example);

    int deleteByExample(VirtualCurrencyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VirtualCurrency record);

    int insertSelective(VirtualCurrency record);

    List<VirtualCurrency> selectByExample(VirtualCurrencyExample example);

    VirtualCurrency selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VirtualCurrency record, @Param("example") VirtualCurrencyExample example);

    int updateByExample(@Param("record") VirtualCurrency record, @Param("example") VirtualCurrencyExample example);

    int updateByPrimaryKeySelective(VirtualCurrency record);

    int updateByPrimaryKey(VirtualCurrency record);
}