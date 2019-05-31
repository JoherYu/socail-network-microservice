package com.sharePhoto.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Joher
 * @data 2019/5/31
 **/
public interface LoginService {

    Map<String, Object> login(HttpServletRequest request, HttpServletResponse response,Map<String ,Object> data);
}
