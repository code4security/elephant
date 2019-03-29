package com.sjhy.platform.client.deploy.config;

/**
 * 错误详情
 */
public enum IosMsg {
    OK("成功","成功"),
    ERROR_UNKNOWN("未知错误","未知错误"),
    ERROR_BAN("已封禁","改玩家已被封禁"),
    ERROR_CLIENT_VALUE("客户端传送参数有误","登录失败"),
    ERROR_FAILURE("验证支付参数失败","支付失败"),
    SEND_MAIL_GIFT("发送邮件奖励","发送邮件奖励");

    private String innerMsg;
    private String msg;

    IosMsg(String innerMsg, String msg) {
        this.innerMsg = innerMsg;
        this.msg = msg;
    }

    public String getInnerMsg(){return innerMsg;}

    public String getMsg(){return msg;}
}
