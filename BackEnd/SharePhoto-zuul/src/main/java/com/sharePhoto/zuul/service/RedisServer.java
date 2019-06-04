package com.sharePhoto.zuul.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Joher
 * @data 2019/5/31
 **/

//@FeignClient(value = "share-photo-redis", fallback = RedisHystrix.class)
@FeignClient(value = "share-photo-redis")
public interface RedisServer {

    @PostMapping("/put")
    public boolean put(
            @RequestParam(value = "key") String key,
            @RequestParam(value = "value") String value,
            @RequestParam(value = "seconds") long seconds);

    @GetMapping("/get")
    public String get(@RequestParam(value = "key") String key);

    @DeleteMapping("/delete")
    public boolean delete(@RequestParam(value = "key") String key);
}
