package com.sjhy.platform.test;

import com.sjhy.platform.biz.game.ChannelBo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @HJ
 */
@RestController
public class ChannelController {
    @Resource
    private ChannelBo channelBo;

    @RequestMapping(value = "/getChannel.do", method = RequestMethod.GET)
    public String getChannel(String channelId){
        return channelBo.queryChannelId(channelId);
    }

    @RequestMapping(value = "verifyChannel.do",method = RequestMethod.GET)
    public String verifyChannel(String channelId,Integer gameId,String version){
        ChannelAndVersion channelAndVersion = new ChannelAndVersion();
        channelAndVersion.setChannelId(channelId);
        channelAndVersion.setGameId(gameId);
        channelAndVersion.setVersionNum(version);
        return channelBo.verifyChannel(channelAndVersion);
    }

}
