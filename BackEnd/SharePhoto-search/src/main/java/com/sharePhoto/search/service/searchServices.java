package com.sharePhoto.search.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Joher
 * @data 2019/6/6
 **/
public interface searchServices {

    // 搜索
    Map<String, Object> search(String category, String q, Integer page, HttpServletRequest request);
}
