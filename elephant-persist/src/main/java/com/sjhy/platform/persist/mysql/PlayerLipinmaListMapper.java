package com.sjhy.platform.persist.mysql;

import com.sjhy.platform.client.dto.base.PlayerLipinmaList;
import com.sjhy.platform.client.dto.base.PlayerLipinmaListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlayerLipinmaListMapper {
    int countByExample(PlayerLipinmaListExample example);

    int deleteByExample(PlayerLipinmaListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PlayerLipinmaList record);

    int insertSelective(PlayerLipinmaList record);

    List<PlayerLipinmaList> selectByExample(PlayerLipinmaListExample example);

    PlayerLipinmaList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PlayerLipinmaList record, @Param("example") PlayerLipinmaListExample example);

    int updateByExample(@Param("record") PlayerLipinmaList record, @Param("example") PlayerLipinmaListExample example);

    int updateByPrimaryKeySelective(PlayerLipinmaList record);

    int updateByPrimaryKey(PlayerLipinmaList record);
}