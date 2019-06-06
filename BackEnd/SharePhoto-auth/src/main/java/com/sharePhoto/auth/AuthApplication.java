package com.sharePhoto.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Joher
 * @data 2019/6/5
 **/

@EnableDiscoveryClient
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@MapperScan("com.sharePhoto.auth.dao")
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
