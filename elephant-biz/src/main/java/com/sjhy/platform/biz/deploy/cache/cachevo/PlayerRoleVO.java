package com.sjhy.platform.biz.deploy.cache.cachevo;

import com.kairo.gameserver.domain.PlayerRole;

import java.io.Serializable;

/**
 * 缓存角色对象信息
 * 任何属性发生改变都需要重新读取数据库并计算属性
 * @author SHACS
 *
 */
public class PlayerRoleVO extends PlayerRole implements Serializable{
	private static final long serialVersionUID = 1L;
	
}
