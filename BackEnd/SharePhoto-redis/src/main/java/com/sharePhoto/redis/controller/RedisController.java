package com.sharePhoto.redis.controller;


import com.sharePhoto.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    private RedisService redisService;

    @PostMapping("/put")
    public boolean put(String key, String value, long seconds) {
        redisService.put(key, value, seconds);
        return true;
    }

    @GetMapping("/get")
    public String get(String key) {
        Object o = redisService.get(key);
        if (o != null) {
            String json = ( String ) o;
            return json;
        }
        return null;
    }

    @DeleteMapping("/delete")
    public boolean delete(String key) {
        redisService.delete(key);
        return true;
    }
}
