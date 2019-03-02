package com.sjhy.platform.test;

import com.sjhy.platform.biz.deploy.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisTest {
    public static void main(String[] args) {
        RedisService redis = new RedisService();
        redis.set("a","hellow");

        String b = (String) redis.get("a");
        System.out.println(b);
    }
}
