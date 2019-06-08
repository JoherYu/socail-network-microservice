package com.sharePhoto.auth.controller;

import com.sharePhoto.auth.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Joher
 * @data 2019/6/5
 **/

@Controller
public class AuthController {

    @Resource
    AuthService authService;

    @PostMapping("/user")
    @ResponseBody
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

    @GetMapping(value = "/confirm/{token:.+}", produces = "text/html;charset=UTF-8")
    public String confirm(@PathVariable("token") String token, HttpServletRequest request, Model model){
        String message = "";
        try {
             message = authService.confirm(token, request);
        } catch (NullPointerException e) {
            e.printStackTrace();
            message = "请先登录";
        } catch (Exception e) {
            e.printStackTrace();
            message = "您没有通过验证";
        }
        model.addAttribute("message", message);
        return "auth";
    }
}
