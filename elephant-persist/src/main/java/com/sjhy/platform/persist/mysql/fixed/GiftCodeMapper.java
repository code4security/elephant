package com.sjhy.platform.persist.mysql.fixed;

import com.sjhy.platform.client.dto.fixed.GiftCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface GiftCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GiftCode record);

    int insertSelective(GiftCode record);

    GiftCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GiftCode record);

    int updateByPrimaryKey(GiftCode record);

    // 查询礼品码
    GiftCode selectByCode(@Param("giftCode") String giftCode);

    /**
     * 判断是否存在礼品  存在就
     * @param giftCode
     * @return
     */
    int isHas(String giftCode);

    int expired(@Param(value = "id") Integer id, @Param(value = "beginTime")String beginTime, @Param(value = "endTime")String endTime);

    /**
     * 查询 礼品码批次号
     * @param giftCode
     * @return
     */
    GiftCode queryListId(@Param("giftCode") String giftCode);

    /**
     * 使用过的注册码 更改他的list为0
     * @param giftCode
     */
    void updateByUse(String giftCode);
}