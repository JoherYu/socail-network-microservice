package com.sharePhoto.dataFaker.config;

import com.sharePhoto.common.service.ReadWriteSplittingService.DBContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Joher
 * @data 2019/6/2
 **/

@Aspect
@Component
public class DataSourceAop {

    @Pointcut("execution(* com.sharePhoto.dataFaker.dao..*.select*(..))")
    public void readPointcut() {

    }

    @Pointcut("execution(* com.sharePhoto.dataFaker.dao..*.insert*(..)) || execution(* com.sharePhoto.dataFaker.dao..*.update*(..)) || execution(* com.sharePhoto.dataFaker.dao..*.delete*(..))")
    public void writePointcut() {

    }
    @Before("readPointcut()")
    public void read() {
        DBContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write() {
        DBContextHolder.master();
    }
}
