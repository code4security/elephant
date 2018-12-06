package com.sjhy.platform.persist.mysql;

import com.sjhy.platform.client.dto.base.GameJumpSite;
import com.sjhy.platform.client.dto.base.GameJumpSiteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GameJumpSiteMapper {
    int countByExample(GameJumpSiteExample example);

    int deleteByExample(GameJumpSiteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GameJumpSite record);

    int insertSelective(GameJumpSite record);

    List<GameJumpSite> selectByExample(GameJumpSiteExample example);

    GameJumpSite selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GameJumpSite record, @Param("example") GameJumpSiteExample example);

    int updateByExample(@Param("record") GameJumpSite record, @Param("example") GameJumpSiteExample example);

    int updateByPrimaryKeySelective(GameJumpSite record);

    int updateByPrimaryKey(GameJumpSite record);
}