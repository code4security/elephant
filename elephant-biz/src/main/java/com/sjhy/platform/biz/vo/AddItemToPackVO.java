package com.sjhy.platform.biz.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 该VO是用来标记放入包裹中的物品id与数量的,包括:战斗掉落物品,任务获取物品等,不是用来与前台通信的,请勿删除
 * @author SHACS
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemToPackVO {
	private int itemId; // 物品ID
	private int itemNum; // 物品数量
	// private short strengthenLevel = 1; // 物品的强化等级
}
