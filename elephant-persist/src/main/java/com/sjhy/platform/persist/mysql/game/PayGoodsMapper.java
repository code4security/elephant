package com.sjhy.platform.persist.mysql.game;

import com.sjhy.platform.client.dto.game.PayGoods;

public interface PayGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PayGoods record);

    int insertSelective(PayGoods record);

    PayGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayGoods record);

    int updateByPrimaryKey(PayGoods record);
}