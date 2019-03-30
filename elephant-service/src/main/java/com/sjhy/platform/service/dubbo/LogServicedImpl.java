package com.sjhy.platform.service.dubbo;

import com.sjhy.platform.biz.bo.LogBO;
import com.sjhy.platform.client.service.LogServiced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "LogServiced")
public class LogServicedImpl implements LogServiced {
    @Autowired
    private LogBO logBO;

    @Override
    public void saveMdlog(long roleId, String gameId, String type, String body) {
        logBO.saveMdlog(roleId,gameId,type,body);
    }
}
