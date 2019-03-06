package com.sjhy.platform.persist.mysql.history;

import com.sjhy.platform.client.dto.history.PlayerPayLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerPayLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlayerPayLog record);

    int insertSelective(PlayerPayLog record);

    PlayerPayLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlayerPayLog record);

    int updateByPrimaryKey(PlayerPayLog record);

    // 查询玩家支付订单
    List<PlayerPayLog> selectByPayLog(@Param("gameId") String gameId, @Param("roleId") Long roleId);

    // 查询玩家买过解锁内容
    PlayerPayLog selectByOpen(@Param("gameId") String gameId, @Param("roleId") Long roleId);

    // 查询玩家是否购买过商品
    PlayerPayLog selectByGood(@Param("gameId") String gameId, @Param("roleId") Long roleId, @Param("goodsName") String goodsName);

    // 查询所有数量
    int countByPlayerPayLog(PlayerPayLog record);

    // ios查询玩家订单
    PlayerPayLog selectByIosPayLog(@Param("gameId") String gameId, @Param("roleId") Long roleId, @Param("orderId") String orderId);
}