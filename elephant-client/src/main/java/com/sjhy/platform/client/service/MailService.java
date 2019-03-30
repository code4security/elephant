package com.sjhy.platform.client.service;
/**
 * @HJ
 */
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.dto.vo.AddItemToPackVO;
import com.sjhy.platform.client.dto.vo.MailVO;
import com.sjhy.platform.client.dto.vo.ReturnVo;

import java.util.List;

public interface MailService {
    // 删除邮件
    ResultDTO deleteMail(ServiceContext sc, int mailId);

    // 获取邮件物品
    ResultDTO<ReturnVo> getMailItem(ServiceContext sc, int mailId, int doType);

    // 查询最新邮件数量
    ResultDTO<Integer> getNewMailNum(ServiceContext sc);

    // 获取邮件列表
    ResultDTO<List<MailVO>> getMailList(ServiceContext sc, int from, int to);
}
