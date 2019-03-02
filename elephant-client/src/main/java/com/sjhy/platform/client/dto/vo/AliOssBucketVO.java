package com.sjhy.platform.client.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AliOssBucketVO implements Serializable {
	private String bucket;
	private String objkey;
	
	private long gold;
	private String gameId;
	private String channelId;
	
	private int operateId;

	private String metaMd5;
	
	private Date updateTime;
	private String saveTime;
	
	private String fname;
	private int type;
}
