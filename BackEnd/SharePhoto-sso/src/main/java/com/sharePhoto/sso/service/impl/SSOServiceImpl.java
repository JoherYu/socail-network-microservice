package com.sharePhoto.sso.service.impl;

import com.alibaba.fastjson.JSON;
import com.sharePhoto.common.CookieUtils;
import com.sharePhoto.common.service.entity.User;
import com.sharePhoto.sso.consumer.RedisServer;
import com.sharePhoto.sso.dao.UserMapper;
import com.sharePhoto.sso.service.SSOService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.sharePhoto.common.GeneratorPassword.generateHash;

/**
 * @author Joher
 * @data 2019/5/31
 **/

@Service("sso")
public class SSOServiceImpl implements SSOService {

    @Resource
    UserMapper userMapper;

    @Resource
    RedisServer redisServer;

    @Override
    @Transactional
    public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response, Map<String, Object> data) {
        Map<String, Object> message = new HashMap<>();

        String token = CookieUtils.getCookieValue(request, "token");

        if (data == null){
            message.put("message", "无效的登录参数");
            message.put("type", "warning");
            return message;
        }

        if (token != null && redisServer.get(token) != null){
            message.put("message", "您现在处于登录状态，请先退出帐号再进行登录操作");
            message.put("type", "info");
            return message;
        }

        String email = (String) data.get("email");
        String username = null;
        try {
            username = userMapper.selectUsernameByEmail(email.toLowerCase());
        } catch (NullPointerException e) {
            e.printStackTrace();
            message.put("message", "用户名不存在");
            message.put("type", "warning");
            return message;
        }
        if (username == null){
            message.put("message", "用户名不存在");
            message.put("type", "warning");
            return message;
        }

        String oriUsername = userMapper.selectOriUsernaem(username);
        String passwordHash = generateHash(oriUsername, (String)data.get("password"));

        User user = userMapper.selectLoginInfo(email);
        if (!user.isActive()){
            message.put("message", "此用户已被封禁");
            message.put("type", "warning");
            return message;
        }
        if (!passwordHash.equals(user.getPasswordHash())){
            message.put("message", "用户名或密码错误");
            message.put("type", "warning");
            return message;
        }

        Date time = new Date();
        long currentTime = time.getTime();

        token = UUID.randomUUID().toString();
        Map<String, Object> cache = new HashMap<>();
        cache.put("id", user.getId());
        cache.put("username", user.getUsername());
        cache.put("oriUsername", user.getOriUsername());
        cache.put("email", user.getEmail());
        cache.put("time", currentTime);
        String json = JSON.toJSONString(cache);

        String CSRFtoken = UUID.randomUUID().toString();
        Map<String, Object> newCSRF = new HashMap<>();
        newCSRF.put(CSRFtoken, currentTime);
        String CSRFjson = JSON.toJSONString(newCSRF);


        boolean rememberMe = (boolean) data.get("rememberMe");
        if (!rememberMe){
            try {
                redisServer.put(token, json, 60 * 60 * 24);
                redisServer.put(CSRFtoken, CSRFjson, 60 * 60 * 24);
                CookieUtils.setCookie(request, response, "token", token);
            } catch (Exception e) {
                e.printStackTrace();
                message.put("message", "服务错误，请稍后再试");
                message.put("type", "warning");
                return message;
            }
        } else {
            try {
                redisServer.put(token, json, 60 * 60 * 24 * 90);
                redisServer.put(CSRFtoken, CSRFjson, 60 * 60 * 90);
                CookieUtils.setCookie(request, response, "token", token, 60 * 60 * 24 * 90);
            } catch (Exception e) {
                e.printStackTrace();
                message.put("message", "服务错误，请稍后再试");
                message.put("type", "warning");
                return message;
            }
        }

        Map<String, Object> result = new HashMap<>();
        User userInfo = userMapper.selectLoginUserInfoByEmail(email);
        result.put("username",userInfo.getUsername());
        result.put("notificationCount",userInfo.getNotificationCount());
        result.put("avatarS",userInfo.getAvatarS());
        result.put("avatarM",userInfo.getAvatarM());
        result.put("name",userInfo.getName());
        result.put("canModerate",userInfo.isCanModerate());
        result.put("canComment",userInfo.isCanComment());
        result.put("isAdmin",userInfo.isAdmin());
        result.put("message","恭喜您登录成功！");
        result.put("type", "success");
        result.put("rememberMe", rememberMe);
        result.put("csrfToken", CSRFtoken);
        return result;
    }

    @Override
    public Map<String, Object> logout(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> message = new HashMap<>();

        String token = CookieUtils.getCookieValue(request, "token");

        if (token == null && redisServer.get(token) == null){
            message.put("message", "对不起，您还未登录或登录信息已过期");
            message.put("type", "info");
            return message;
        }

        try {
            redisServer.delete(token);
            CookieUtils.deleteCookie(request, response, token);
            String aa = request.getHeader("X-CSRFToken");
            redisServer.delete(aa);
        } catch (Exception e) {
            e.printStackTrace();
            message.put("message", "服务错误，请稍后再试");
            message.put("type", "warning");
            return message;
        }

        String CSRFtoken = UUID.randomUUID().toString();
        Date time = new Date();
        long currentTime = time.getTime();

        Map<String, Object> newCSRF = new HashMap<>();
        newCSRF.put(CSRFtoken, currentTime);
        String CSRFjson = JSON.toJSONString(newCSRF);
        try {
            redisServer.put(CSRFtoken, CSRFjson, 60 * 60 * 24);
        } catch (Exception e) {
            e.printStackTrace();
            message.put("message", "服务错误，请稍后再试");
            message.put("type", "warning");
            return message;
        }
        Map<String, Object> result = new HashMap<>();
        result.put("message","您已成功退出");
        result.put("type", "success");
        result.put("csrfToken", CSRFtoken);
        return result;
    }
}
