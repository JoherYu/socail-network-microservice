package com.sharePhoto.tiny.service.service;

import com.rabbitmq.client.Channel;

import java.util.Map;

/**
 * @author Joher
 * @data 2019/6/3
 **/
public interface TinyServices {

    Map<String, String> getCSRFToken();

    void sendEmail(org.springframework.amqp.core.Message mailMessage,  Channel channel) throws Exception;

}
