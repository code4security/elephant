package com.sjhy.platform.client.service;
/**
 * @HJ
 */
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.dto.vo.AliOssAccessKeyVO;
import com.sjhy.platform.client.dto.vo.AliOssBucketVO;
import com.sjhy.platform.client.dto.vo.ReturnVo;

import java.util.List;

public interface OssService {
    // 删除oss文件
    ResultDTO delBucketObjkey(ServiceContext sc, int operateId);

    // 获取oss文件Bucketkeys
    ResultDTO<List<AliOssAccessKeyVO>> getBucketkeys(ServiceContext sc, int keyType);

    // 取得用户上传信息
    ResultDTO<List<AliOssBucketVO>> getBucket(ServiceContext sc);

    // 更新上传进度(客户端通知服务器上传结束)
    ResultDTO notifyEndPut(ServiceContext sc, int operateId, int result, String md5);

    // 申请上传操作
    ResultDTO<ReturnVo> putBucket(ServiceContext sc, String objKey, long gold, String saveTime, String fname, int objType);
}
