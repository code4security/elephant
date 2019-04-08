package com.sjhy.platform.persist.mysql.history;

import com.sjhy.platform.client.dto.history.PlayerGiftLog;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface PlayerGiftLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlayerGiftLog record);

    int insertSelective(PlayerGiftLog record);

    PlayerGiftLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlayerGiftLog record);

    int updateByPrimaryKey(PlayerGiftLog record);

    //验证是否使用过激活码
    PlayerGiftLog selectByUseGiftCode(PlayerGiftLog record);

    //检查激活码是否使用过
    PlayerGiftLog selectByGiftCode(PlayerGiftLog record);

    /**
     *  检查激活码是否使用过 2
     * @param map
     * @return
     */
    int ishas(Map<String, Object> map);
}