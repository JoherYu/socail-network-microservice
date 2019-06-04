package com.sharePhoto.tiny.service.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            @RequestParam(value = "value") String value,
            @RequestParam(value = "seconds") long seconds);

    @GetMapping("get")
    public Map<String, Object> get(@RequestParam(value = "key") String key);
}
