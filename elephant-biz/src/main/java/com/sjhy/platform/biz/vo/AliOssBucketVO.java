package com.sjhy.platform.biz.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AliOssBucketVO {
	private String bucket;
	private String objkey;
	
	private long gold;
	private int gameId;
	
	private int operateId;

	private String metaMd5;
	
	private Date updateTime;
	private String saveTime;
	
	private String fname;
	private int type;
}
