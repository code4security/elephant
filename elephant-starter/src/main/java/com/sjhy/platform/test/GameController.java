package com.sjhy.platform.test;

import com.sjhy.platform.biz.game.GameBo;
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
    private GameBo gameBo;

    @RequestMapping(value = "/getGame.do",method = RequestMethod.GET)
    public String getGame(int gameId){
       return gameBo.queryGameId(gameId);
    }
}
