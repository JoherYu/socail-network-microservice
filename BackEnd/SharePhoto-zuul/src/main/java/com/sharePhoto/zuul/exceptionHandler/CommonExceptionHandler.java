package com.sharePhoto.zuul.exceptionHandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Joher
 * @data 2019/6/5
 **/

@ControllerAdvice
public class CommonExceptionHandler {

//    private static final Logger LOGGER = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    Map<String, String> handleException(){
        Map<String ,String> message = new HashMap<>();
        message.put("message", "服务错误，请稍后再试");
        message.put("type", "warning");
        return message;
    }
}
