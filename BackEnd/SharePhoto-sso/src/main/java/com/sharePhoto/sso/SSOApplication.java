package com.sharePhoto.sso;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Joher
 * @data 2019/5/31
 **/

@EnableEurekaClient
@EnableFeignClients
@EnableDiscoveryClient
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@MapperScan("com.sharePhoto.sso.dao")
public class SSOApplication {
    public static void main(String[] args) {
        SpringApplication.run(SSOApplication.class, args);
    }
}
