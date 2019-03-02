package com.sjhy.platform.client.dto.enumerate;

/**
 * 模块
 * @author HJ
 *
 */
public enum ModuleEnum {
	ACTIVATIONCODE(1, "activationCode"),
	NULL(-1, "null")
	;
	
	private int moduleId;
	private String moduleName;
	
	private ModuleEnum(int moduleId, String moduleName) {
		this.moduleId = moduleId;
		this.moduleName = moduleName;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public static ModuleEnum modOf(String value)  {
		ModuleEnum[] values = ModuleEnum.values();
    	for (ModuleEnum enumValue : values) {
			if(value.equals(enumValue.moduleName))
				return enumValue;
		}
    	return NULL;
    }
}
