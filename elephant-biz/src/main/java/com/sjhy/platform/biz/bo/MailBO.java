package com.sjhy.platform.biz.bo;

import com.sjhy.platform.client.dto.game.Mail;
import com.sjhy.platform.persist.mysql.game.MailMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @HJ
 */
@Service
public class MailBO {
    @Resource
    MailMapper mailMapper;

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public void deleteMail(long roleId, long mailId) {
        Mail deleteMail = new Mail();

        deleteMail.setId((int) mailId);
        deleteMail.setStatus(true);

        mailMapper.updateByPrimaryKeySelective(deleteMail);
    }
}
