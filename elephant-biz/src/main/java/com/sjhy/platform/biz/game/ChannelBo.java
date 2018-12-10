package com.sjhy.platform.biz.game;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @HJ
 * 渠道
 */
@Service
public class ChannelBo {
    @Resource
    private ChannelAndVersionMapper channelAndVersionMapper;

    public String queryChannelId(String channelId){
        ChannelAndVersion channelAndVersion = channelAndVersionMapper.selectByPrimaryKey(channelId);
        if (channelAndVersion != null){
            return "OK,"+channelAndVersion.toString();
        }else {
            return "NO，不存在该渠道";
        }
    }

    public String verifyChannel(ChannelAndVersion channelAndVersion){
        ChannelAndVersion channel = channelAndVersionMapper.selectByGameAndChannel(channelAndVersion);
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
