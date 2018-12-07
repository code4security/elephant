package com.sjhy.platform.persist.mysql.game;

import com.sjhy.platform.client.dto.game.Mail;

public interface MailMapper {
    int deleteByPrimaryKey(Long mailId);

    int insert(Mail record);

    int insertSelective(Mail record);

    Mail selectByPrimaryKey(Long mailId);

    int updateByPrimaryKeySelective(Mail record);

    int updateByPrimaryKey(Mail record);
}