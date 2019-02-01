package com.sjhy.platform.service.dubbo;

import com.sjhy.platform.biz.bo.PlayerBO;
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.biz.deploy.exception.AccountAlreadyBindingOtherException;
import com.sjhy.platform.biz.deploy.exception.NotExistAccountException;
import com.sjhy.platform.client.dto.player.Player;
import com.sjhy.platform.client.dto.vo.AccountVO;
import com.sjhy.platform.client.service.PlayerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @HJ
 */
@Service(value = "PlayerService")
public class PlayerServiceImpl implements PlayerService {
    @Resource
    private PlayerBO playerBO;

    @Override
    /**
     * 获取渠道验证
     */
    public ResultDTO<AccountVO> getAccount(ServiceContext sc, String deviceUniqueID) {
        try {
            return ResultDTO.getSuccessResult(playerBO.getAccount(sc, deviceUniqueID));
        } catch (AccountAlreadyBindingOtherException e) {
            e.printStackTrace();
        } catch (NotExistAccountException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * 查询玩家总数
     */
    public ResultDTO<Integer> getTotalPlayerNum() {
        return ResultDTO.getSuccessResult(playerBO.getTotalPlayerNum());
    }

    @Override
    /**
     * 根据玩家唯一标示创建玩家id
     */
    public ResultDTO<Player> createNewPlayerByCooperate(ServiceContext sc, String deviceUniquelyId, String deviceModel, String ip) {
        return ResultDTO.getSuccessResult(playerBO.createNewPlayerByCooperate(sc, deviceUniquelyId, deviceModel, ip));
    }

    @Override
    /**
     * 创建渠道用户id
     */
    public ResultDTO createPlayer(ServiceContext sc) {
        playerBO.createPlayer(sc);
        return null;
    }

    @Override
    /**
     * 验证是否需要激活码
     */
    public ResultDTO<Boolean> checkModule(ServiceContext sc, String moduleName) {
        return ResultDTO.getSuccessResult(playerBO.checkModule(sc, moduleName));
    }
}
