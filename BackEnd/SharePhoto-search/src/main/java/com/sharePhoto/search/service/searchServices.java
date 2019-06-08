package com.sharePhoto.search.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author Joher
 * @data 2019/6/6
 **/
public interface searchServices {

    // 搜索
    Map<String, Object> search(String category, String q, Integer page, HttpServletRequest request);

    // 新增UserDocument
    void insertUser(Message message, Channel channel) throws IOException;
}
