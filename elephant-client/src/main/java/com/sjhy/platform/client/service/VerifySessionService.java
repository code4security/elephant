package com.sjhy.platform.client.service;

import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;

import java.util.Map;

/**
 *
 */
public interface VerifySessionService {
    // 验证会话服务
    ResultDTO<Map<String, String>> verifySession(ServiceContext sc, String sessionId, String deviceUniqueID, int bindFlag, String pmtChannelId, String verifyId);
}
