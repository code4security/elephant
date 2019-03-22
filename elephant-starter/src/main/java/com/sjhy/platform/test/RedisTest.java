package com.sjhy.platform.test;

import com.sjhy.platform.biz.deploy.redis.RedisUtil;
import com.sjhy.platform.biz.deploy.utils.DbVerifyUtils;
import com.sjhy.platform.client.dto.game.Game;
import com.sjhy.platform.persist.mysql.game.GameMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class RedisTest {
    @Autowired
    private RedisUtil redis;
    @Autowired
    private GameMapper game;

    public void getRedis(){
        Game games = new Game();
        games.setId(1);
        games.setNameCn("灰鹃");
        redis.set("game",games);
        System.out.println(redis.get("game"));
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }
}
