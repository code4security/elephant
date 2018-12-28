package com.sjhy.platform.biz.bo;

import com.sjhy.platform.client.dto.utils.UtilDate;
import com.sjhy.platform.client.dto.common.GiftCode;
import com.sjhy.platform.client.dto.game.GiftCodeList;
import com.sjhy.platform.client.dto.history.PlayerGiftLog;
import com.sjhy.platform.persist.mysql.common.GiftCodeMapper;
import com.sjhy.platform.persist.mysql.game.GiftCodeListMapper;
import com.sjhy.platform.persist.mysql.history.PlayerGiftLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 * @HJ
 */
@Service
public class GiftCodeBO {
    @Resource
    private PlayerGiftLogMapper playerGiftLogMapper;
    @Resource
    private GiftCodeMapper giftCodeMapper;
    @Resource
    private GiftCodeListMapper giftCodeListMapper;

    /**
     * 查询是否使用过激活码
     * @param gameId
     * @param playerId
     * @return
     */
    public boolean isMeActivated(String gameId, long playerId){
        // 是否使用过激活码
        PlayerGiftLog playerGiftLog = new PlayerGiftLog();
        playerGiftLog.setGameId(gameId);
        playerGiftLog.setPlayerId(playerId);
        // 查询
        PlayerGiftLog lipinmaLog = playerGiftLogMapper.selectByUseGiftCode(playerGiftLog);
        if(lipinmaLog != null){
            return true;
        }
        return false;
    }

    /**
     * 使用激活码
     * @param code
     * @param gameId
     * @param playerId
     * @param channelId
     * @return
     */
    public int activateCode(String code, String gameId, long playerId, String channelId) {
        // 激活码是否有效
        GiftCodeList giftCodeList = isValidActivationCode(code, channelId, gameId, playerId);
        if(giftCodeList == null){
            return -1;
        }

        PlayerGiftLog playerGiftLog = new PlayerGiftLog();

        playerGiftLog.setGiftCode(code);
        playerGiftLog.setPlayerId(playerId);
        playerGiftLog.setGameId(gameId);
        playerGiftLog.setChannelId(channelId);
        playerGiftLog.setActivateTime(new Date());
        playerGiftLog.setGiftListId(giftCodeList.getId());

        return playerGiftLogMapper.insert(playerGiftLog);
    }

    /**
     * 激活码是否有效，并未被使用过
     * @param code
     * @param channelId
     * @param gameId
     * @param playerId
     * @return
     */
    public GiftCodeList isValidActivationCode(String code, String channelId, String gameId, long playerId) {
        // 码信息取得
        GiftCode giftCode = giftCodeMapper.selectByGiftCode(code);
        if(giftCode == null){
            return null;
        }

        // 码种类取得
        GiftCodeList maType = giftCodeListMapper.selectByPrimaryKey(giftCode.getGiftListId());
        if(maType == null){
            return null;
        }

        if(maType.getType() == 0){
            // 判断是否在使用时间内
            Date begin = UtilDate.text2Date(maType.getBeginTime());
            Date end = UtilDate.text2Date(maType.getEndTime());

            if(begin.getTime() > Calendar.getInstance().getTime().getTime() || end.getTime() < Calendar.getInstance().getTime().getTime()){
                //logger.error("兑换码已过期|code="+code);
                return null;
            }

            if(gameId != maType.getGameId()){
                //logger.error("不同游戏不能使用|code="+code);
                return null;
            }

            if(!channelId.equals(maType.getChannelId())){
                //logger.error("不同渠道不能使用|code="+code);
                return null;
            }

            // 是否被使用过
            PlayerGiftLog playerGiftLog = new PlayerGiftLog();
            playerGiftLog.setGameId(gameId);
            playerGiftLog.setGiftCode(code);

            PlayerGiftLog lipinmaLog = playerGiftLogMapper.selectByGiftCode(playerGiftLog);
            if(playerGiftLog != null){
                //logger.error("游戏已使用|code="+code);
                return null;
            }
            return maType;
        }
        return null;
    }
}
