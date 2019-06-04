package com.sharePhoto.sso.controller;

import com.sharePhoto.sso.service.SSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class SSOController {

    @Autowired
    SSOService SSOService;

    @PostMapping("/session")
    public Map<String, Object> login (HttpServletRequest request, HttpServletResponse response,@RequestBody Map<String, Object> data) {
        return  SSOService.login(request, response, data);
    }

    @DeleteMapping("/session")
    public Map<String, Object> logout (HttpServletRequest request, HttpServletResponse response) {
        return  SSOService.logout(request, response);
    }
}
