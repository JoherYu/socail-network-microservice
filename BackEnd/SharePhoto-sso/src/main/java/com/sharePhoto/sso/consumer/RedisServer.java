package com.sharePhoto.sso.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Joher
 * @data 2019/5/31
 **/

@FeignClient(value = "share-photo-redis")
public interface RedisServer {

    @PostMapping("put")
    public boolean put(
            @RequestParam(value = "key") String key,
            @RequestParam(value = "value") Map<String, Object> value,
            @RequestParam(value = "seconds") long seconds);

    @GetMapping("get")
    public Map<String, Object> get(@RequestParam(value = "key") String key);
}
