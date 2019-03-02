package com.sjhy.platform.persist.mysql.fixed;

import com.sjhy.platform.client.dto.fixed.GiftCode;
import org.apache.ibatis.annotations.Param;

public interface GiftCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GiftCode record);

    int insertSelective(GiftCode record);

    GiftCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GiftCode record);

    int updateByPrimaryKey(GiftCode record);

    // 查询礼品码
    GiftCode selectByCode(@Param("giftCode") String giftCode);
}