package com.sharePhoto.search.controller;


import com.sharePhoto.search.service.searchServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Joher
 * @data 2019/6/6
 **/
@RestController
public class searchController {

    @Autowired
    searchServices searchServices;

    @GetMapping("/search/{category}/{q}")
    public Map<String, Object> search(@PathVariable("category") String category, @PathVariable("q") String q, @RequestParam(value = "page", defaultValue = "1") Integer page, HttpServletRequest request) {
        return searchServices.search(category, q, page, request);
    }
}
