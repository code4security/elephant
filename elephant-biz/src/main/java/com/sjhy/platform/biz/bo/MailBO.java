package com.sjhy.platform.biz.bo;

import com.sjhy.platform.client.dto.config.KairoErrorCode;
import com.sjhy.platform.client.dto.enumerate.MailTypeEnum;
import com.sjhy.platform.client.dto.exception.MailItemErrorException;
import com.sjhy.platform.client.dto.exception.MailNotBelongThisRoleException;
import com.sjhy.platform.client.dto.exception.NoSuchRoleException;
import com.sjhy.platform.client.dto.vo.AddItemToPackVO;
import com.sjhy.platform.client.dto.vo.MailVO;
import com.sjhy.platform.client.dto.vo.PlayerRoleVO;
import com.sjhy.platform.client.dto.game.Mail;
import com.sjhy.platform.persist.mysql.game.MailMapper;
import com.sjhy.platform.persist.mysql.player.PlayerRoleMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @HJ
 */
@Service
public class MailBO {
    private static final Logger logger = Logger.getLogger(MailBO.class);
    @Resource
    private MailMapper mailMapper;
    @Resource
    private PlayerRoleMapper playerRoleMapper;

    /**
     * 删除邮件
     * @param roleId
     * @param mailId
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public void deleteMail(long roleId, int mailId) {
        Mail deleteMail = new Mail();

        deleteMail.setId(mailId);
        deleteMail.setStatus(true);

        mailMapper.updateByPrimaryKeySelective(deleteMail);
    }

    /**
     * 获取邮件物品
     * @param roleId
     * @param mailId
     * @param doType
     * @return
     * @throws NoSuchRoleException
     * @throws MailNotBelongThisRoleException
     * @throws MailItemErrorException
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public List<AddItemToPackVO> getMailItem(long roleId, int mailId, int doType, String gameId) throws NoSuchRoleException, MailNotBelongThisRoleException, MailItemErrorException
    {
        PlayerRoleVO playerRoleVO = (PlayerRoleVO) playerRoleMapper.selectByRoleId(gameId,roleId); // 角色基本信息
        if (playerRoleVO == null) {
            throw new NoSuchRoleException();
        }

        List<AddItemToPackVO> goods = new ArrayList<AddItemToPackVO>();

        Mail mail = mailMapper.selectByMailId(mailId,gameId);
        if(mail == null){
            logger.error("This mail["+mailId+"] is not exist");
            return goods;
        }else if(mail.getStatus()) {
            throw new MailItemErrorException("该邮件已被领取");
        }

        long recvRoleId = mail.getRecvRoleId();
        if (recvRoleId != roleId) {
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
            deleteMail(roleId, mailId);

            // 通知客户端（注释）
            /*GS2C_Mail_Delete_Res.Builder resBuilder = GS2C_Mail_Delete_Res.newBuilder();
            resBuilder.setErrorCode(KairoErrorCode.OK.getErrorCode());
            resBuilder.setMailId(mailId);
             GameServerSendService.getInstance().sendMessage(roleId, new SM_MAIL_DELETE(resBuilder.build().toByteArray()));*/
        }
        return goods;
    }

    /**
     * 查询最新邮件数量
     * @param roleId
     * @Param gameId
     * @return
     * @throws NoSuchRoleException
     */
    public int getNewMailNum(long roleId, String gameId) throws NoSuchRoleException {
        PlayerRoleVO playerRoleVO = (PlayerRoleVO) playerRoleMapper.selectByRoleId(gameId,roleId); // 角色基本信息
        if (playerRoleVO == null) {
            throw new NoSuchRoleException();
        }
        Mail mail = new Mail();
        mail.setRecvRoleId(roleId);
        mail.setGameId(gameId);
        int newMailNum = mailMapper.selcetByRoleMail(mail);

        return newMailNum;
    }

    /**
     * 获取邮件列表
     * @param roleId
     * @param from
     * @param to
     * @return
     * @throws NoSuchRoleException
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public List<MailVO> getMailList(long roleId, String gameId, int from, int to) throws NoSuchRoleException {
        PlayerRoleVO playerRoleVO = (PlayerRoleVO) playerRoleMapper.selectByRoleId(gameId,roleId);

        // 玩家是否存在验证
        if (playerRoleVO == null) {
            throw new NoSuchRoleException();
        }

        Mail mails = new Mail();
        mails.setGameId(gameId);
        mails.setRecvRoleId(roleId);
        List<Mail> roleMailList = mailMapper.selectByRoleId(mails, from, 30);

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
