package com.sjhy.platform.persist.mysql;

import com.sjhy.platform.client.dto.base.LipinmaList;
import com.sjhy.platform.client.dto.base.LipinmaListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LipinmaListMapper {
    int countByExample(LipinmaListExample example);

    int deleteByExample(LipinmaListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LipinmaList record);

    int insertSelective(LipinmaList record);

    List<LipinmaList> selectByExample(LipinmaListExample example);

    LipinmaList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LipinmaList record, @Param("example") LipinmaListExample example);

    int updateByExample(@Param("record") LipinmaList record, @Param("example") LipinmaListExample example);

    int updateByPrimaryKeySelective(LipinmaList record);

    int updateByPrimaryKey(LipinmaList record);
}