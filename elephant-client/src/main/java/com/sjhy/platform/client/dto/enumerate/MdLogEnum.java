package com.sjhy.platform.client.dto.enumerate;

/**
 * 埋点ID
 * @author Administrator
 *
 */
public enum MdLogEnum {
	RECHARGE(1000, "recharge"),// 支付
	
	NOVICE_GUIDE(1100, "novice_guide"),// 新手引导
	COPY(1200, "copy"),// 副本关卡
	GAME_CHARACTER(1300, "game_character"),// 游戏人物
	PET(1400, "pet"),// 宠物
	ITEM(1500, "item"),// 道具
	MEDAL(1600, "medal"),// 奖牌
	TOKEN_MONEY(1700, "token_money"),// 代币
	DIFFICULTY_MODES(1800, "difficulty_modes"),// 难度模式
	
	PLAYERLEV(2000, "playerlev"),// 难度模式
	LINEAD(2100, "linead"),
	SHILI(2200, "shili"),
	HUODONG(2300, "huodong"),
	NETFIGHT(2500, "netfight"),
	
	MOVIESHOW(2600, "movieshow"),
	
	LOGERROR(9000, "logerror"),
	NULL(-1, "null")
	;
	
	private int mdType;
	private String mdLogName;
	
	private MdLogEnum(int mdType, String mdLogName) {
		this.setMdType(mdType);
		this.setMdLogName(mdLogName);
	}

	public static MdLogEnum valueOf(int value)  {
		MdLogEnum[] values = MdLogEnum.values();
    	for (MdLogEnum enumValue : values) {
			if(value == enumValue.getMdType())
				return enumValue;
		}

    	return NULL;
    }
	
	public static MdLogEnum nameValueOf(String value)  {
		MdLogEnum[] values = MdLogEnum.values();
    	for (MdLogEnum enumValue : values) {
			if(value.equals(enumValue.mdLogName))
				return enumValue;
		}

    	return NULL;
    }

	public int getMdType() {
		return mdType;
	}

	public void setMdType(int mdType) {
		this.mdType = mdType;
	}

	public String getMdLogName() {
		return mdLogName;
	}

	public void setMdLogName(String mdLogName) {
		this.mdLogName = mdLogName;
	}
}
