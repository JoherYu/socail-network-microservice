package com.sharePhoto.auth.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Joher
 * @data 2019/6/5
 **/
public interface AuthService {

    // 添加新用户
    Map<String, String> addUser(Map<String, Object> authData, HttpServletRequest request) throws Exception;
}
