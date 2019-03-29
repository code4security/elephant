package com.sjhy.platform.client.service;

import com.sjhy.platform.client.dto.player.PlayerRole;
import com.sjhy.platform.client.dto.vo.BulletinLoginVO;

import java.util.List;

public interface DbVerify {
    boolean isHasGame(String gameId);

    boolean isHasChannel(String channelId,String gameId);

    boolean isHasIos(Long iosId, String gameId, String channelId);

    PlayerRole isHasRole(String gameId, Long playerId, Long roleId);

    void remoteRole(String gameId,Long roleId);

    List<BulletinLoginVO> isHasNotity(String gameId);
}
