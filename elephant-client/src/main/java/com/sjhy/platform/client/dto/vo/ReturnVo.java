package com.sjhy.platform.client.dto.vo;

import com.sjhy.platform.client.dto.player.PlayerGameOss;
import com.sjhy.platform.client.dto.player.PlayerRole;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Currency;
import java.util.List;

/**
 * 返回结果保证类
 */
@Data
@Component
public class ReturnVo implements Serializable {
    private ReturnVo returnVo;
    // 确认登陆
    private PlayerRole playerRole;
    private String redisSessionKey;
    // 上传bucket
    private AliOssBucketVO aliOssBucketVO;
    private PlayerGameOss playerGameOss;
    // 获取邮件
    private List<AddItemToPackVO> addItemToPackVOList;
    private Integer doType;
    // 封禁玩家
    private Integer banPlayer;
    private String banType;
    // 购买发送奖励
    private Integer state;
    private String goodName;
    private Integer num;
    private Integer payStatus;

    public ReturnVo enterGame(PlayerRole playerRole,String redisSessionKey){
        returnVo = new ReturnVo();
        returnVo.setPlayerRole(playerRole);
        returnVo.setRedisSessionKey(redisSessionKey);
        return returnVo;
    }

    public ReturnVo putBucket(AliOssBucketVO aliOssBucketVO,PlayerGameOss playerGameOss){
        returnVo = new ReturnVo();
        returnVo.setAliOssBucketVO(aliOssBucketVO);
        returnVo.setPlayerGameOss(playerGameOss);
        return returnVo;
    }

    public ReturnVo getMailItem(List<AddItemToPackVO> addItemToPackVOList,Integer doType){
        returnVo = new ReturnVo();
        returnVo.setAddItemToPackVOList(addItemToPackVOList);
        returnVo.setDoType(doType);
        return returnVo;
    }

    public ReturnVo banPlayer(Integer banPlayer,String banType){
        returnVo = new ReturnVo();
        returnVo.setBanPlayer(banPlayer);
        returnVo.setBanType(banType);
        return returnVo;
    }

    public ReturnVo addPayValue(PlayerRole playerRole,Integer state,String goodName,Integer num,Integer payStatus){
        returnVo = new ReturnVo();
        returnVo.setPlayerRole(playerRole);
        returnVo.setState(state);
        returnVo.setGoodName(goodName);
        returnVo.setNum(num);
        returnVo.setPayStatus(payStatus);
        return returnVo;
    }

}
