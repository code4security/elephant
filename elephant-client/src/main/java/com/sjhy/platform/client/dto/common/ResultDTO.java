package com.sjhy.platform.client.dto.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Liu Zheng
 */
@Data
public class ResultDTO<T> implements Serializable {

    protected boolean success;
    protected String key;
    protected String innerMsg;  // 内部错误信息
    protected String msg;   // 暴露客户端错误信息
    protected T result;

    protected ResultDTO(){}

    protected ResultDTO(boolean success, T result, String key, String innerMsg, String msg) {
        this.success = success;
        this.result = result;
        this.key = key;
        this.innerMsg = (innerMsg != null) ? innerMsg: msg;
        this.msg = msg;
    }

    public static <T> ResultDTO<T> getSuccessResult() {
        return new ResultDTO<T>(true, null, null, null, null);
    }

    public static <T> ResultDTO<T> getSuccessResult(T result) {
        return new ResultDTO<T>(true, result, null, null, null);
    }

    public static <T> ResultDTO<T> getFailureResult(String key, String innerMsg, String msg) {
        return new ResultDTO<T>(false, null, key, innerMsg, msg);
    }
}