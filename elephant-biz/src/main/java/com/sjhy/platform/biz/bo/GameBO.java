package com.sjhy.platform.biz.bo;

import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.dto.vo.PlayerRoleVO;
import com.sjhy.platform.client.dto.player.PlayerBanList;
import com.sjhy.platform.persist.mysql.player.PlayerBanListMapper;
import com.sjhy.platform.persist.mysql.player.PlayerRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;

/**
 * @HJ
 */
@Service
public class GameBO {
    private static final Logger logger = LoggerFactory.getLogger(GameBO.class);
    @Resource
    private PlayerRoleMapper playerRoleMapper;
    @Resource
    private PlayerBanListMapper playerBanListMapper;

    /**
     * 查询玩家封禁状态
     * @param ban
     * @return
     */
    public static boolean isRealBan(PlayerBanList ban){
        if(ban != null && ban.getIsBan()){
            // 判断时间是否已过
            if(ban.getBanMinute() * 60 * 1000 + ban.getBanTime().getTime() > Calendar.getInstance().getTimeInMillis()){
                return true;
            }
        }
        return false;
    }

    /**
     * 封停或解封角色
     * @param banType
     * @param minute
     * @return
     */
    public int banPlayer(ServiceContext sc, String banType, int minute){
        // 玩家信息取得
        PlayerRoleVO role = (PlayerRoleVO) playerRoleMapper.selectByPlayerId(sc.getGameId(),sc.getRoleId());
        if(role == null){// 玩家不存在
            return -1;
        }
        boolean isNeedInsert = false;
        // 玩家封停状态取得
        PlayerBanList playerBanList = new PlayerBanList();
        playerBanList.setRoleId(role.getRoleId());
        playerBanList.setGameId(role.getGameId());
        playerBanList.setChannelId(role.getChannelId());
        playerBanList = playerBanListMapper.selectByBan(playerBanList);

        if(playerBanList == null){
            playerBanList = new PlayerBanList();
            isNeedInsert = true;
        }
        if(!isNeedInsert && !isRealBan(playerBanList) && "ban_off".equalsIgnoreCase(banType)){// 玩家不处于封停状态
            return -2;
        }
        if(!isNeedInsert && isRealBan(playerBanList) && "ban_on".equalsIgnoreCase(banType)){// 玩家已处于封停状态
            return -3;
        }

        // 封停或者解除封停逻辑
        if("ban_on".equalsIgnoreCase(banType)){
            playerBanList.setBanMinute(minute);
            playerBanList.setBanTime(Calendar.getInstance().getTime());
            playerBanList.setIsBan(true);
            playerBanList.setRoleId(sc.getRoleId());
            playerBanList.setGameId(sc.getGameId());
            playerBanList.setChannelId(role.getChannelId());

            // 如果玩家在线踢出玩家（注释）
            /* if(sendService.isOnline(roleId)) {
                kickOut(roleId, KairoErrorCode.ERROR_KICKOUT_BY_GM.getErrorCode());
            }*/
        }else if("ban_off".equalsIgnoreCase(banType)){
            playerBanList.setBanMinute(0);
            playerBanList.setBanTime(null);
            playerBanList.setIsBan(false);
        }

        if(isNeedInsert){
            playerBanListMapper.insert(playerBanList);
        }else{
            playerBanListMapper.updateByPrimaryKey(playerBanList);
        }

        return 0;
    }
}
