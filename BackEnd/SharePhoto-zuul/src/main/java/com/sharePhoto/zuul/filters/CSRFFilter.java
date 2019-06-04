package com.sharePhoto.zuul.filters;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.sharePhoto.zuul.service.RedisServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Joher
 * @data 2019/6/4
 **/
@Component
public class CSRFFilter extends ZuulFilter {

    @Autowired
    RedisServer redisServer;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String method = request.getMethod();
        return method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT") || method.equalsIgnoreCase("PATCH") || method.equalsIgnoreCase("DELETE");
    }

    @Override
    public Object run() throws ZuulException {
        Map<String ,String> message = new HashMap<>();

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String token = request.getHeader("X-CSRFToken");
        if (token == null  || redisServer.get(token) == null){
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(200);
            message.put("message", "您的身份信息已过期！请重启浏览器");
            message.put("type", "warning");
            String result = JSON.toJSONString(message);
            context.setResponseBody(result);
        }else {
            context.setSendZuulResponse(true);
        }
        return null;
    }
}
