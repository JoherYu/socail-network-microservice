package com.sharePhoto.redis.controller;


import com.sharePhoto.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RedisController {

    @Autowired
    private RedisService redisService;

    @PostMapping("put")
    public boolean put(String key, Map<String, Object> value, long seconds) {
        redisService.put(key, value, seconds);
        return true;
    }

    @GetMapping("get")
    public Map<String, Object> get(String key) {
        Object o = redisService.get(key);
        if (o != null) {
            Map<String, Object> json = ( Map<String, Object> ) o;
            return json;
        }
        return null;
    }
}
