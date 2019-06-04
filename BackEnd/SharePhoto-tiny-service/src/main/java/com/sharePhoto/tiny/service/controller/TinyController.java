package com.sharePhoto.tiny.service.controller;

import com.sharePhoto.tiny.service.service.TinyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Joher
 * @data 2019/6/3
 **/
@RestController
public class TinyController {

    @Autowired
    TinyServices tinyServices;

    @GetMapping("/csrfToken")
    public Map<String, String> getCSRFToken(){
        return tinyServices.getCSRFToken();
    }
}
