package com.sjhy.platform.client.service;

public interface LogService {
    void saveMdlog(long roleId,String gameId, String type, String body);
}
