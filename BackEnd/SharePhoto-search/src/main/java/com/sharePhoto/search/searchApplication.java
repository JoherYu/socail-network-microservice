package com.sharePhoto.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Joher
 * @data 2019/6/6
 **/

@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableAspectJAutoProxy(proxyTargetClass=true)
@MapperScan("com.sharePhoto.search.dao")
public class searchApplication {
    public static void main(String[] args) {
        SpringApplication.run(searchApplication.class, args);
    }
}
