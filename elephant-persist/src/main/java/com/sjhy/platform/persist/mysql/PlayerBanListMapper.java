package com.sjhy.platform.persist.mysql;

import com.sjhy.platform.client.dto.base.PlayerBanList;
import com.sjhy.platform.client.dto.base.PlayerBanListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlayerBanListMapper {
    int countByExample(PlayerBanListExample example);

    int deleteByExample(PlayerBanListExample example);

    int deleteByPrimaryKey(Long roleId);

    int insert(PlayerBanList record);

    int insertSelective(PlayerBanList record);

    List<PlayerBanList> selectByExample(PlayerBanListExample example);

    PlayerBanList selectByPrimaryKey(Long roleId);

    int updateByExampleSelective(@Param("record") PlayerBanList record, @Param("example") PlayerBanListExample example);

    int updateByExample(@Param("record") PlayerBanList record, @Param("example") PlayerBanListExample example);

    int updateByPrimaryKeySelective(PlayerBanList record);

    int updateByPrimaryKey(PlayerBanList record);
}