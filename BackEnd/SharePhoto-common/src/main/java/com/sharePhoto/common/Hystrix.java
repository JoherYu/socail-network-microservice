package com.sharePhoto.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Joher
 * @data 2019/6/3
 **/
public class Hystrix {

    public static Map<String, Object> defaultHystric (String content){
        Map<String, Object> message = new HashMap<>();
        message.put("message", content + "服务连接失败，请检查您的网络");
        message.put("type", "info");
        return message;
    }
}
