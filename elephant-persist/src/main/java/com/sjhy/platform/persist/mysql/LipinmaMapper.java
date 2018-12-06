package com.sjhy.platform.persist.mysql;

import com.sjhy.platform.client.dto.base.Lipinma;
import com.sjhy.platform.client.dto.base.LipinmaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LipinmaMapper {
    int countByExample(LipinmaExample example);

    int deleteByExample(LipinmaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Lipinma record);

    int insertSelective(Lipinma record);

    List<Lipinma> selectByExample(LipinmaExample example);

    Lipinma selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Lipinma record, @Param("example") LipinmaExample example);

    int updateByExample(@Param("record") Lipinma record, @Param("example") LipinmaExample example);

    int updateByPrimaryKeySelective(Lipinma record);

    int updateByPrimaryKey(Lipinma record);
}