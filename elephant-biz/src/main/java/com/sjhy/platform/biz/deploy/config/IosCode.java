package com.sjhy.platform.biz.deploy.config;

/**
 * ios三国游戏返回状态码
 * 4：添加订单，因意外情况为执行完成代码
 * 5：支付成功
 * 6：代码执行完成，和苹果服务器沟通失败
 */

public enum IosCode {
    OK("10001","成功"),
    ERROR_UNKNOWN("10002","未知错误"),
    ERROR_BAN("10003","已封禁"),
    ERROR_NOT_NOTIFY("10004","暂无公告"),
    ERROR_CLIENT_VALUE("10005","客户端传送参数有误"),
    ERROR_FAILURE("10006","验证支付参数失败"),
    SEND_MAIL_GIFT("10007","发送邮件奖励");

    private String errorCode;
    private String desc;

    IosCode(String errorCode, String desc) {
        this.errorCode = errorCode;
        this.desc = desc;
    }

    public String getErrorCode(){return errorCode;}

    public String getDesc(){return desc;}
}
