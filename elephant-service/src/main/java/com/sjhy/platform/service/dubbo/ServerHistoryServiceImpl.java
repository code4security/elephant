package com.sjhy.platform.service.dubbo;

import com.sjhy.platform.biz.bo.ServerHistoryBO;
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.service.ServerHistoryService;

import javax.annotation.Resource;

/**
 * @HJ
 */
public class ServerHistoryServiceImpl implements ServerHistoryService {

    @Resource
    private ServerHistoryBO serverHistoryBO;

    @Override
    /**
     * 玩家登陆,更新最后登陆服务器列表(内部调用)
     */
    public ResultDTO updateServerHistory(ServiceContext sc) {
        serverHistoryBO.updateServerHistory(sc);
        return null;
    }
}
