package com.sjhy.platform.service.dubbo;

import com.sjhy.platform.biz.bo.MailBO;
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.biz.deploy.exception.MailItemErrorException;
import com.sjhy.platform.biz.deploy.exception.MailNotBelongThisRoleException;
import com.sjhy.platform.biz.deploy.exception.NoSuchRoleException;
import com.sjhy.platform.client.service.MailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @HJ
 */
@Service(value = "MailService")
public class MailServiceImpl implements MailService {

    @Resource
    private MailBO mailBO;

    @Override
    /**
     * 删除邮件
     */
    public ResultDTO deleteMail(ServiceContext sc, int mailId) {
        mailBO.deleteMail(sc, mailId);
        return null;
    }

    @Override
    /**
     * 获取邮件物品
     */
    public ResultDTO<List> getMailItem(ServiceContext sc, int mailId, int doType) {
        try {
            return ResultDTO.getSuccessResult(mailBO.getMailItem(sc, mailId, doType));
        } catch (NoSuchRoleException e) {
            e.printStackTrace();
        } catch (MailNotBelongThisRoleException e) {
            e.printStackTrace();
        } catch (MailItemErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * 查询最新邮件数量
     */
    public ResultDTO<Integer> getNewMailNum(ServiceContext sc) {
        try {
            return ResultDTO.getSuccessResult(mailBO.getNewMailNum(sc));
        } catch (NoSuchRoleException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * 获取邮件列表
     */
    public ResultDTO<List> getMailList(ServiceContext sc, int from, int to) {
        try {
            return ResultDTO.getSuccessResult(mailBO.getMailList(sc,from,to));
        } catch (NoSuchRoleException e) {
            e.printStackTrace();
        }
        return null;
    }
}
