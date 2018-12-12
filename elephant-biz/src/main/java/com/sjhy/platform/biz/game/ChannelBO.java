package com.sjhy.platform.biz.game;

import com.sjhy.platform.client.dto.game.ChannelAndVersion;
import com.sjhy.platform.persist.mysql.game.ChannelAndVersionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @HJ
 * 渠道
 */
@Service
public class ChannelBO {
    @Resource
    private ChannelAndVersionMapper channelAndVersionMapper;

    public String verifyChannel(ChannelAndVersion channelAndVersion){
        ChannelAndVersion channel = channelAndVersionMapper.verifyChannel(channelAndVersion);
        if (channel != null){
            if (channelAndVersion.getVersionNum().equals(channel.getVersionNum()) || channelAndVersion.getVersionNum() == channel.getVersionNum()){
                return "OK,"+channel.toString();
            }else {
                return "请跳转以下网址下载最新版本："+channel.getVersionDownload();
            }
        }else {
            return "NO，不存在该渠道或者游戏";
        }
    }
}
