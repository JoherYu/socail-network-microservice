package com.sharePhoto.sso.controller;

import com.sharePhoto.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Joher
 * @data 2019/5/31
 **/

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/session")
    public Map<String, Object> login (HttpServletRequest request, HttpServletResponse response,@RequestBody Map<String, Object> data) {
        return  loginService.login(request, response, data);
    }
}
