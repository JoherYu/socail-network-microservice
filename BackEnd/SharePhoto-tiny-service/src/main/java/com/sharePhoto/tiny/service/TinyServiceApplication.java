package com.sharePhoto.tiny.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Joher
 * @data 2019/6/3
 **/

@EnableEurekaClient
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class TinyServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TinyServiceApplication.class, args);
    }
}
