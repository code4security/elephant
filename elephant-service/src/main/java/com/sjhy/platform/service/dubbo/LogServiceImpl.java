package com.sjhy.platform.service.dubbo;

import com.sjhy.platform.biz.bo.LogBO;
import com.sjhy.platform.client.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;

public class LogServiceImpl implements LogService {
    @Autowired
    private LogBO logBO;

    @Override
    public void saveMdlog(long roleId, String gameId, String type, String body) {
        logBO.saveMdlog(roleId,gameId,type,body);
    }
}
