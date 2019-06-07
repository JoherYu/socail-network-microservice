package com.sharePhoto.common.interceptor.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.sharePhoto.common.CookieUtils;
import com.sharePhoto.common.interceptor.redis.RedisServer;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author Joher
 * @data 2019/6/7
 **/

public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    RedisServer redisServer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String token = CookieUtils.getCookieValue(request, "token");

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        if (token == null || redisServer.get(token) == null){
            PrintWriter pw = response.getWriter();
            JSONObject res = new JSONObject();
            res.put("message","请先进行登录");
            res.put("type", "warning");
            pw.append(res.toString());
            return false;
        }else {
            return true;
        }
    }
}
