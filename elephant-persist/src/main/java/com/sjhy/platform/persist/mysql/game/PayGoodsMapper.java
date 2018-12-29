package com.sjhy.platform.persist.mysql.game;

import com.sjhy.platform.client.dto.game.PayGoods;
import org.apache.ibatis.annotations.Param;

public interface PayGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PayGoods record);

    int insertSelective(PayGoods record);

    PayGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayGoods record);

    int updateByPrimaryKey(PayGoods record);

    // 根据渠道id 查询商品
    PayGoods selectByGChannelId(@Param("goodsName") String goodsName, @Param("channelId") String channelId, @Param("gameId") String gameId);
}