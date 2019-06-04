package com.sharePhoto.dataFaker;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Joher
 * @data 2019/6/2
 **/

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableAspectJAutoProxy(proxyTargetClass=true)
@MapperScan("com.sharePhoto.dataFaker.dao")
public class dataFakerApplication {
    public static void main(String[] args) {
        SpringApplication.run(dataFakerApplication.class, args);
    }
}
