package com.sjhy.platform;

import com.sjhy.platform.client.dto.base.Game;
import com.sjhy.platform.persist.mysql.GameMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @HJ
 */
@RestController
public class GameController {
    @Resource
    private GameMapper gameMapper;

    @RequestMapping(value = "/get.do",method = RequestMethod.GET)
    public String getGame(int id){
        Game game = gameMapper.selectByPrimaryKey(id);

        if (game == null){
            return "no";
        }else {
            return "ok\n"+game.toString();
        }
    }
}
