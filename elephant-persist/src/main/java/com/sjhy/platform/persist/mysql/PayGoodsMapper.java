package com.sjhy.platform.persist.mysql;

import com.sjhy.platform.client.dto.base.PayGoods;
import com.sjhy.platform.client.dto.base.PayGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PayGoodsMapper {
    int countByExample(PayGoodsExample example);

    int deleteByExample(PayGoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PayGoods record);

    int insertSelective(PayGoods record);

    List<PayGoods> selectByExample(PayGoodsExample example);

    PayGoods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PayGoods record, @Param("example") PayGoodsExample example);

    int updateByExample(@Param("record") PayGoods record, @Param("example") PayGoodsExample example);

    int updateByPrimaryKeySelective(PayGoods record);

    int updateByPrimaryKey(PayGoods record);
}