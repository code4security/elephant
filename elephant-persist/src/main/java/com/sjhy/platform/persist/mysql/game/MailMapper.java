package com.sjhy.platform.persist.mysql.game;

import com.sjhy.platform.client.dto.game.Mail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Mail record);

    int insertSelective(Mail record);

    Mail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Mail record);

    int updateByPrimaryKey(Mail record);

    // 查询邮件
    Mail selectByMailId(@Param("id") Integer id, @Param("gameId") String gameId);

    // 查询角色所有未领取邮件
    int selcetByRoleMail(Mail record);

    // 查询邮件列表
    List<Mail> selectByRoleId(Mail record, @Param("from")int from, @Param("to")int to);
}