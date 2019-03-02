package com.sjhy.platform.client.service;
/**
 * @HJ
 */

import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;

public interface ServerHistoryService {
    // 玩家登陆,更新最后登陆服务器列表(内部调用)
    ResultDTO updateServerHistory(ServiceContext sc);
}
