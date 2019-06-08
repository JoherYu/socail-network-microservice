package com.sharePhoto.tiny.service.service;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.sharePhoto.tiny.service.consumer.RedisServer;
import com.sharePhoto.tiny.service.utils.JsonSerilizable;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * @author Joher
 * @data 2019/6/3
 **/
@Service("tiny")
public class TinyServicesImpl implements TinyServices {

    @Autowired
    RedisServer redisServer;

    @Override
    public Map<String, String> getCSRFToken() {
        Map<String, String> result = new HashMap<>();
        String token = UUID.randomUUID().toString();

        Map<String, Object> cache = new HashMap<>();
        Date time = new Date();
        long currentTime = time.getTime();
        cache.put(token, currentTime);

        String json = JSON.toJSONString(cache);
        try {
            redisServer.put(token, json, 60 * 60 * 24);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "服务错误，请稍后再试");
            result.put("type", "warning");
            return result;
        }

        result.put("csrfToken", token);
        return result;
    }

    @Override
    @RabbitListener(queues = "mail")
    public void sendEmail(org.springframework.amqp.core.Message rabbit, Channel channel) throws Exception {
        Map<String, String> mailMessage = JsonSerilizable.deserilizableForMapFromFile(new String(rabbit.getBody()), String.class);

        //1、连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", "smtp.163.com");
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        //设置调试信息在控制台打印出来
        session.setDebug(true);
        //3、创建邮件的实例对象
        Message msg = getMimeMessage(session, mailMessage.get("content"), mailMessage.get("subject"), mailMessage.get("email"));
        //4、根据session对象获取邮件传输对象Transport
        Transport transport = session.getTransport();
        //设置发件人的账户名和密码
        transport.connect("testingforweb@163.com", "授权码");
        //如果只想发送给指定的人，可以如下写法
        transport.sendMessage(msg, new Address[]{new InternetAddress(mailMessage.get("email"))});
        //5、关闭邮件连接
        transport.close();
        // 返回ACK
        channel.basicAck(rabbit.getMessageProperties().getDeliveryTag(), true);
    }

    private static MimeMessage getMimeMessage(Session session, String content, String subject, String recipientAddress) throws Exception{
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //设置发件人地址
        msg.setFrom(new InternetAddress("testingforweb@163.com"));
        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(recipientAddress));
        //设置邮件主题
        msg.setSubject(subject,"UTF-8");
        //设置邮件正文
        msg.setContent(content, "text/html;charset=UTF-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());

        return msg;
    }
}
