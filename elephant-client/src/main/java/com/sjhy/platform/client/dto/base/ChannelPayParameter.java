package com.sjhy.platform.client.dto.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设置渠道支付回调参数信息表
 * 
 * @author HJ
 * 
 * @date 2018-12-06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChannelPayParameter {
    /**
     * ID
     */
    private Integer id;

    /**
     * 游戏id
     */
    private Integer gameId;

    /**
     * 渠道id
     */
    private String channelId;

    /**
     * 子渠道
     */
    private Integer subChannelId;

    /**
     * 操作系统
     */
    private String os;

    private String appKey;

    private String appSecret;

    private String appId;

    private String publicKey;

    private String privateKey;

    private String payPublicKey;

    private String payPrivateKey;

    private String serverId;

    private String merchantId;

    private String cpId;

    private String payKey;

    /**
     * 回调下载地址
     */
    private String notifyUrl;

}