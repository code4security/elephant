package com.sjhy.platform.biz.game;

import com.sjhy.platform.client.dto.game.Game;
import com.sjhy.platform.persist.mysql.game.GameMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @HJ
 * 游戏
 */
@Service
public class GameBo {
    @Resource
    private GameMapper gameMapper;

    public String queryGameId(int gameId){
        Game game = gameMapper.selectByPrimaryKey(gameId);
        if (game != null){
            return "OK";
        }else {
            return "NO，不存在该游戏";
        }
    }
}
