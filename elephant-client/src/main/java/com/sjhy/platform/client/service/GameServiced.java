package com.sjhy.platform.client.service;
/**
 * @HJ
 */
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.dto.player.PlayerBanList;
import com.sjhy.platform.client.dto.vo.ReturnVo;

public interface GameServiced {

    // 查询玩家封禁状态
    ResultDTO<Boolean> isRealBan(PlayerBanList ban);

    // 封停或解封角色
    ResultDTO<ReturnVo> banPlayer(ServiceContext sc, String banType, int minute);

}
