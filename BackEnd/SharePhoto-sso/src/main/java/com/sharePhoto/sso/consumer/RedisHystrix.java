package com.sharePhoto.sso.consumer;

import org.springframework.stereotype.Component;

/**
 * @author Joher
 * @data 2019/6/3
 **/
@Component
public class RedisHystrix implements RedisServer {
    @Override
    public boolean put(String key, String value, long seconds) {
        return false;
    }

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public boolean delete(String key) {
        return false;
    }
}
