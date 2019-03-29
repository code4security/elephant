package com.sjhy.platform.client.service;
/**
 * @HJ
 */
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.dto.player.PlayerRole;

public interface RoleService {
    // 创建游戏角色第一步
    ResultDTO<PlayerRole> createNewPlayer(ServiceContext sc, String roleName, String deviceToken);

    // 创建游戏角色第二步
    ResultDTO<PlayerRole> createPlayerRole(ServiceContext sc, String roleName);

    // 更新指定玩家的最后登录时间
    ResultDTO updateLastLoginTime(ServiceContext sc);

    // 验证玩家名字是否重复
    ResultDTO checkNewPlayerName(ServiceContext sc, String admiralName);
}
