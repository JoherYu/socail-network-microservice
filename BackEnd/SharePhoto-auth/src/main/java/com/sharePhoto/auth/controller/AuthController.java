package com.sharePhoto.auth.controller;

import com.sharePhoto.auth.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Joher
 * @data 2019/6/5
 **/

@RestController
public class AuthController {

    @Resource
    AuthService authService;

    @PostMapping("/user")
    public Map<String, String> register(@RequestBody Map<String ,Object> data, HttpServletRequest request){
        try {
            return authService.addUser(data, request);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> message = new HashMap<>();
            message.put("message","注册失败，请稍后再试");
            message.put("type","warning");
            return message;
        }
    }
}
