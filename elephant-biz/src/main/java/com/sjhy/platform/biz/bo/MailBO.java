package com.sjhy.platform.biz.bo;

import com.sjhy.platform.client.deploy.exception.MailItemErrorException;
import com.sjhy.platform.client.deploy.exception.MailNotBelongThisRoleException;
import com.sjhy.platform.client.deploy.exception.NoSuchRoleException;
import com.sjhy.platform.biz.utils.DbVerifyUtils;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.deploy.enumerate.MailTypeEnum;
import com.sjhy.platform.client.dto.player.PlayerRole;
import com.sjhy.platform.client.dto.vo.AddItemToPackVO;
import com.sjhy.platform.client.dto.vo.MailVO;
import com.sjhy.platform.client.dto.game.Mail;
import com.sjhy.platform.client.dto.vo.ReturnVo;
import com.sjhy.platform.persist.mysql.game.MailMapper;
import com.sjhy.platform.persist.mysql.player.PlayerRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @HJ
 */
@Service
public class MailBO {
    private static final Logger logger = LoggerFactory.getLogger(MailBO.class);
    @Resource
    private MailMapper mailMapper;
    @Autowired
    private DbVerifyUtils dbVerifyUtils;
    @Resource
    private ReturnVo returnVo;

    /**
     * 删除邮件
     * @param mailId
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public void deleteMail(ServiceContext sc, int mailId) {
        Mail deleteMail = new Mail();

        deleteMail.setId(mailId);
        deleteMail.setGameId(sc.getGameId());
        deleteMail.setRecvRoleId(sc.getRoleId());
        deleteMail.setStatus((short) 3);

        mailMapper.updateByPrimaryKeySelective(deleteMail);
    }

    /**
     * 获取邮件物品
     * @param mailId
     * @param doType
     * @return
     * @throws NoSuchRoleException
     * @throws MailNotBelongThisRoleException
     * @throws MailItemErrorException
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public ReturnVo getMailItem(ServiceContext sc, int mailId, int doType) throws NoSuchRoleException, MailNotBelongThisRoleException, MailItemErrorException
    {
        PlayerRole playerRoleVO = dbVerifyUtils.isHasRole(sc.getGameId(),null,sc.getRoleId());
        if (playerRoleVO == null) {
            throw new NoSuchRoleException();
        }

        List<AddItemToPackVO> goods = new ArrayList<AddItemToPackVO>();

        Mail mail = mailMapper.selectByMailId(mailId,sc.getGameId());
        if(mail == null){
            logger.error("This mail["+mailId+"] is not exist");
            return returnVo.getMailItem(goods,0);
        }else if(mail.getStatus()>2) {
            throw new MailItemErrorException("该邮件已被领取");
        }

        long recvRoleId = mail.getRecvRoleId();
        if (recvRoleId != sc.getRoleId()) {
            throw new MailNotBelongThisRoleException();
        }

        if(doType == 1) {
            int goodId = mail.getGoodsId();
            int numbers = mail.getGoodsNum();
            short mailType = mail.getType();

            // 添加物品---道具
            if(goodId > 0 && numbers > 0){
                goods.add(new AddItemToPackVO(goodId, numbers));
            }
            if (goods.size() <= 0 || mailType == MailTypeEnum.Normal.getValue()) {
                throw new MailItemErrorException();
            }
        }else if(doType == 2) {
            // 删除邮件
            deleteMail(sc, mailId);

            // 通知客户端（注释）（1）
            /*GS2C_Mail_Delete_Res.Builder resBuilder = GS2C_Mail_Delete_Res.newBuilder();
            resBuilder.setErrorCode(KairoErrorCode.OK.getErrorCode());
            resBuilder.setMailId(mailId);
             GameServerSendService.getInstance().sendMessage(roleId, new SM_MAIL_DELETE(resBuilder.build().toByteArray()));*/
        }
        return returnVo.getMailItem(goods,doType);
    }

    /**
     * 查询最新邮件数量
     * @return
     * @throws NoSuchRoleException
     */
    public int getNewMailNum(ServiceContext sc) throws NoSuchRoleException {
        PlayerRole playerRoleVO = dbVerifyUtils.isHasRole(sc.getGameId(),sc.getPlayerId(),sc.getRoleId());
        if (playerRoleVO == null) {
            throw new NoSuchRoleException();
        }
        Mail mail = new Mail();
        mail.setRecvRoleId(sc.getRoleId());
        mail.setGameId(sc.getGameId());
        int newMailNum = mailMapper.selcetByRoleMail(mail);

        return newMailNum;
    }

    /**
     * 获取邮件列表
     * @param from
     * @param to
     * @return
     * @throws NoSuchRoleException
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public List<MailVO> getMailList(ServiceContext sc, int from, int to) throws NoSuchRoleException {
        PlayerRole playerRoleVO = dbVerifyUtils.isHasRole(sc.getGameId(),sc.getPlayerId(),sc.getRoleId());
        // 玩家是否存在验证
        if (playerRoleVO == null) {
            throw new NoSuchRoleException();
        }

        Mail mails = new Mail();
        mails.setGameId(sc.getGameId());
        mails.setRecvRoleId(sc.getRoleId());
        List<Mail> roleMailList = mailMapper.selectByRoleId(sc.getRoleId(), sc.getGameId(), from, 30);

        List<MailVO> roleMailVOList = new ArrayList<MailVO>();
        for (Mail mail : roleMailList) {
            MailVO mailVO = new MailVO();

            mailVO.setMailId(mail.getId());
            mailVO.setMailType(MailTypeEnum.valueOf(mail.getType()));
            mailVO.setSendTime(mail.getSendTime());
            mailVO.setTitle(mail.getTitle());
            mailVO.setContext(mail.getContext());
            mailVO.setStatus(mail.getStatus());

            if(mail.getGoodsId() != null){
                mailVO.setGoodId(mail.getGoodsId());
                mailVO.setGoodNum(mail.getGoodsNum());
            }
            roleMailVOList.add(mailVO);
        }
        return roleMailVOList;
    }
}
