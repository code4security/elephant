package com.sjhy.platform.client.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录成功之后的返回值
 * 
 * @author HJ
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegularLoginVO
{
	private long playerId;
	
	private Srp6VO srp6Info = new Srp6VO();
	
	private List<GameServerVO> gameServerList = new ArrayList<GameServerVO>();
	
	private String playerName;
	private int playerDegree;
	private String playerAvatar;
	private boolean isNeedActivation;
}
