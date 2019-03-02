package com.sjhy.platform.client.dto.common;

import com.sjhy.platform.client.dto.common.ResultDTO;
import lombok.Data;

/**
 * @author Liu Zheng
 */
@Data
public class PageResultDTO<T> extends ResultDTO<T> {

    protected Integer totalCount;

    protected PageResultDTO(){}

    protected PageResultDTO(boolean success, T result, String key, String innerMsg, String msg, Integer totalCount) {
        super(success, result, key, innerMsg, msg);
        this.totalCount = totalCount;
    }

    public static <T> PageResultDTO<T> getSuccessResult() {
        return new PageResultDTO<T>(true, null, null, null, null, 0);
    }

    public static <T> PageResultDTO<T> getSuccessResult(int totalCount, T result) {
        return new PageResultDTO<T>(true, result, null, null, null, totalCount);
    }

    public static <T> PageResultDTO<T> getFailureResult(String key, String innerMsg, String msg) {
        return new PageResultDTO<T>(false, null, key, innerMsg, msg, null);
    }
}
