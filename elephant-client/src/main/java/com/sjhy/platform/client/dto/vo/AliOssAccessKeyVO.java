package com.sjhy.platform.client.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AliOssAccessKeyVO implements Serializable {
    private String Expiration;
    private String keyId;
    private String keySecret;
    private String token;
    
    private int keyType;
    
    private String endPoint;
    
    // key更新时间
    private Date updateTime;
    
    private String gameId;
    private String channelId;
}
