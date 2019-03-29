package com.sjhy.platform.service.dubbo;

import com.sjhy.platform.biz.bo.RoleBO;
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.deploy.exception.*;
import com.sjhy.platform.client.dto.player.PlayerRole;
import com.sjhy.platform.client.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @HJ
 */
@Service(value = "RoleService")
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleBO roleBO;

    @Override
    /**
     * 创建游戏角色第一步
     */
    public ResultDTO<PlayerRole> createNewPlayer(ServiceContext sc, String roleName, String deviceToken) {
        try {
            return ResultDTO.getSuccessResult(roleBO.createNewPlayer(sc, roleName, deviceToken));
        } catch (NoSuchRoleException e) {
            e.printStackTrace();
        } catch (AlreadyExistsPlayerRoleException e) {
            e.printStackTrace();
        } catch (CreateRoleException e) {
            e.printStackTrace();
        } catch (GameIdIsNotExsitsException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * 创建游戏角色第二步
     */
    public ResultDTO<PlayerRole> createPlayerRole(ServiceContext sc, String roleName) {
        return ResultDTO.getSuccessResult(roleBO.createPlayerRole(sc, roleName));
    }

    @Override
    /**
     * 更新指定玩家的最后登录时间
     */
    public ResultDTO updateLastLoginTime(ServiceContext sc) {
        roleBO.updateLastLoginTime(sc);
        return null;
    }

    @Override
    /**
     * 验证玩家名字是否重复
     */
    public ResultDTO checkNewPlayerName(ServiceContext sc, String admiralName) {
        try {
            roleBO.checkNewPlayerName(sc,admiralName);
        } catch (AdmiralNameIsNotNullableException e) {
            e.printStackTrace();
        } catch (AdmiralNameIsTooLongException e) {
            e.printStackTrace();
        } catch (AdmiralNameIncludeHarmonyException e) {
            e.printStackTrace();
        } catch (AdmiralNameCoincideException e) {
            e.printStackTrace();
        }
        return null;
    }

}
