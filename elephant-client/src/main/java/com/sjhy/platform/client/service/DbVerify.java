package com.sjhy.platform.client.service;

import com.sjhy.platform.client.dto.game.GameNotify;
import com.sjhy.platform.client.dto.player.PlayerGameOss;
import com.sjhy.platform.client.dto.player.PlayerRole;

import java.util.List;

public interface DbVerify {
    boolean isHasGame(String gameId);

    boolean isHasChannel(String channelId,String gameId);

    boolean isHasIos(Long iosId, String gameId, String channelId);

    PlayerRole isHasRole(String gameId, Long playerId, Long roleId);

    void remoteRole(String gameId,Long roleId);

    GameNotify isHasNotity(String gameId);

    Integer isHasLastServer(String gameId, Long roleId);

    List<PlayerGameOss> isHasOss(String gameId, Long roleId);

    PlayerGameOss isHasOssGameKey(Integer id,String gameId);

    PlayerGameOss isHasOssObjKey(PlayerGameOss ossObj);

    PlayerGameOss ossSelectByPrimaryKey(Integer id);

    void ossUpdateByPrimaryKeySelective(PlayerGameOss playerGameOss);

    void ossUpdateEndtimeByRoleId(String gameId,Long roleId);
}
