package com.sjhy.platform.service.dubbo;

import com.sjhy.platform.biz.bo.OssBO;
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.dto.exception.*;
import com.sjhy.platform.client.dto.vo.AliOssBucketVO;
import com.sjhy.platform.client.service.OssService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @HJ
 */
@Service(value = "OssService")
public class OssServiceImpl implements OssService {
    @Resource
    private OssBO ossBO;

    @Override
    /**
     * 删除oss文件
     */
    public ResultDTO delBucketObjkey(ServiceContext sc, int operateId) {
        ossBO.delBucketObjkey(sc,operateId);
        return null;
    }

    @Override
    /**
     * 获取oss文件Bucketkeys
     */
    public ResultDTO<List> getBucketkeys(ServiceContext sc, int keyType) {
        try {
            return ResultDTO.getSuccessResult(ossBO.getBucketkeys(sc, keyType));
        } catch (OssBucketkeyException e) {
            e.printStackTrace();
        } catch (NoSuchRoleException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * 取得用户上传信息
     */
    public ResultDTO<List> getBucket(ServiceContext sc) {
        try {
            return ResultDTO.getSuccessResult(ossBO.getBucket(sc));
        } catch (NoSuchRoleException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * 更新上传进度(客户端通知服务器上传结束)
     */
    public ResultDTO notifyEndPut(ServiceContext sc, int operateId, int result, String md5) {
        try {
            ossBO.notifyEndPut(sc, operateId, result, md5);
        } catch (KairoException e) {
            e.printStackTrace();
        } catch (NoSuchRoleException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * 申请上传操作
     */
    public ResultDTO<AliOssBucketVO> putBucket(ServiceContext sc, String objKey, long gold, String saveTime, String fname, int objType) {
        try {
            return ResultDTO.getSuccessResult(ossBO.putBucket(sc, objKey, gold, saveTime, fname, objType));
        } catch (NoSuchRoleException e) {
            e.printStackTrace();
        } catch (AdmiralNameIsTooLongException e) {
            e.printStackTrace();
        } catch (IsHaveSpecialCharacterException e) {
            e.printStackTrace();
        } catch (KairoException e) {
            e.printStackTrace();
        }
        return null;
    }
}
