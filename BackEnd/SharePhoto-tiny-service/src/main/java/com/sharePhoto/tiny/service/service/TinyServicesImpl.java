package com.sharePhoto.tiny.service.service;

import com.alibaba.fastjson.JSON;
import com.sharePhoto.tiny.service.consumer.RedisServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Joher
 * @data 2019/6/3
 **/
@Service
public class TinyServicesImpl implements TinyServices {

    @Autowired
    RedisServer redisServer;

    @Override
    public Map<String, String> getCSRFToken() {
        Map<String, String> result = new HashMap<>();
        String token = UUID.randomUUID().toString();

        Map<String, Object> cache = new HashMap<>();
        Date time = new Date();
        long currentTime = time.getTime();
        cache.put(token, currentTime);

        String json = JSON.toJSONString(cache);
        try {
            redisServer.put(token, json, 60 * 60 * 24);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "服务错误，请稍后再试");
            result.put("type", "warning");
            return result;
        }

        result.put("csrfToken", token);
        return result;
    }
}
