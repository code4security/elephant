package com.sjhy.platform.client.dto.base;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @HJ
 */
@Data
public class RegularLoginVO {

    private long accountId;

    private Srp6VO srp6Info = new Srp6VO();

    private List<GameServerVO> gameServerList = new ArrayList<GameServerVO>();

    private String playerName;

    private int playerDegree;

    private String playerAvatar;

    private boolean isNeedActivation;
}
