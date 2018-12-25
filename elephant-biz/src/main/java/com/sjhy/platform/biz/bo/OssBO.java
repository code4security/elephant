package com.sjhy.platform.biz.bo;

import com.sjhy.platform.biz.vo.AliOssAccessKeyVO;
import com.sjhy.platform.client.dto.player.PlayerGameOss;
import com.sjhy.platform.persist.mysql.player.PlayerGameOssMapper;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OssBO {
    private static Logger logger = Logger.getLogger(OssBO.class);

    // 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
    private static final String REGION_CN_HANGZHOU = "cn-hangzhou";
    // 当前 STS API 版本
    private static final String STS_API_VERSION = "2015-04-01";
    private static final String BUCKET_KEY = "kairo-game-data";
    private static final String BUCKET_KEY_BACK = "kairo-game-back";
    // key存活时间、单位秒
    private static final int DURATION_SECONDS = 20;
    private static String BaseGameFilePath = null;
    // 上传状态值定义
    private static final int UPLOADING = 1;
    private static final int SUCCESSED = 2;
    private static final int ERROR     = -1;
    private static final int DELETED   = -2;
    // 只有 RAM用户（子账号）才能调用 AssumeRole 接口
    // 阿里云主账号的AccessKeys不能用于发起AssumeRole请求
    // 请首先在RAM控制台创建一个RAM用户，并为这个用户创建AccessKeys
    private static final String RAM_ACCESS_KEY = "LTAIwWUwQr4yVEr1";
    private static final String RAM_ACCESS_KEY_SECRET = "1YIVlGkLyMY1B0JoIfzvCAgrFLyDeK";
    // 此为OSS最大权限账户,用于查询obj信息
    private static final String ACCESS_KEY = "LTAIUvkgCcZ9AofR";
    private static final String ACCESS_KEY_SECRET = "MfyFW2rMk3OnigcWy3HTJrhdCXFcSY";
    // 玩家accesskey保存{roleId{'get/put', AliOssAccessKeyVO}}
    private static Map<Long, ConcurrentHashMap<String, AliOssAccessKeyVO>> C_Player_Accesskey_Map = new ConcurrentHashMap<Long, ConcurrentHashMap<String, AliOssAccessKeyVO>>();
    // 以杭州为例
    private static final String ENDPOINT = "http://oss-cn-hangzhou.aliyuncs.com";

    @Resource
    private PlayerGameOssMapper playerGameOssMapper;

    /**
     * 删除oss文件
     * @param roleId
     * @param operateId
     */
    public void delBucketObjkey(long roleId, int operateId, String gameId){
        // 删除文件信息
        PlayerGameOss playerGameOss = playerGameOssMapper.selectByGameKey(operateId,gameId);
        if(playerGameOss == null){
            return;
        }
        // 异常情况处理
        if(playerGameOss.getRoleId() != roleId){
            return;
        }
        // 更新状态
        playerGameOss.setStatus(DELETED);
        playerGameOss.setUpdateTime(new Date());
        playerGameOss.setEndTime(null);
        playerGameOss.setMetaMd5(null);

        playerGameOssMapper.updateByPrimaryKeySelective(playerGameOss);
    }
}
