package com.sjhy.platform.persist.mysql;

import com.sjhy.platform.client.dto.base.Mail;
import com.sjhy.platform.client.dto.base.MailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MailMapper {
    int countByExample(MailExample example);

    int deleteByExample(MailExample example);

    int deleteByPrimaryKey(Long mailId);

    int insert(Mail record);

    int insertSelective(Mail record);

    List<Mail> selectByExample(MailExample example);

    Mail selectByPrimaryKey(Long mailId);

    int updateByExampleSelective(@Param("record") Mail record, @Param("example") MailExample example);

    int updateByExample(@Param("record") Mail record, @Param("example") MailExample example);

    int updateByPrimaryKeySelective(Mail record);

    int updateByPrimaryKey(Mail record);
}