package com.sjhy.platform.biz.deploy.redis;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CacheObject {
	private String region;
	private Object key;
	private Object value;
	private byte level;
}