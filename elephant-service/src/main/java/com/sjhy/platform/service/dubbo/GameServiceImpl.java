package com.sjhy.platform.service.dubbo;

import com.sjhy.platform.biz.bo.GameBO;
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.dto.player.PlayerBanList;
import com.sjhy.platform.client.service.GameService;

import javax.annotation.Resource;

/**
 * @HJ
 */
public class GameServiceImpl implements GameService {

    @Resource
    private GameBO gameBO;

    @Override
    /**
     * 查询玩家封禁状态
     */
    public ResultDTO<Boolean> isRealBan(PlayerBanList ban) {
        return ResultDTO.getSuccessResult(gameBO.isRealBan(ban));
    }

    @Override
    /**
     * 封停或解封角色
     */
    public ResultDTO<Integer> banPlayer(ServiceContext sc, String banType, int minute) {
        return ResultDTO.getSuccessResult(gameBO.banPlayer(sc,banType,minute));
    }
}
