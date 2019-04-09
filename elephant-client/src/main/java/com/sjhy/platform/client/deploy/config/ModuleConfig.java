package com.sjhy.platform.client.deploy.config;

/**
 * 模块管理
 */
public enum ModuleConfig {

    MODULE("Module","模块"),
    HOT_UPDATE("HotUpdate","热更新");

    private String module;
    private String detailed;

    ModuleConfig(String module, String detailed) {
        this.module = module;
        this.detailed = detailed;
    }

    public String getModule(){return module;}

    public String getDetailed(){return detailed;}
}
