package com.sharePhoto.auth.service;

import com.sharePhoto.auth.dao.UserMapper;
import com.sharePhoto.auth.utils.JWTToken;
import com.sharePhoto.auth.utils.generateAvatar.GenerateAvatar;
import com.sharePhoto.common.service.entity.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sharePhoto.common.GeneratorPassword.generateHash;

/**
 * @author Joher
 * @data 2019/6/5
 **/
@Service("auth")
public class AuthServiceImpl implements AuthService {

    @Resource
    private GenerateAvatar generateAvatar;

    @Resource
    private UserMapper userMapper;

    @Resource
    private AmqpTemplate amqpTemplate;

    @Override
    @Transactional
    public Map<String, String> addUser(Map<String, Object> authData, HttpServletRequest request) throws Exception {
        Map<String, String> data = (Map<String, String>) authData.get("authData");

        User user = new User();
        user.setEmail(data.get("email").toLowerCase());
        user.setUsername(data.get("username"));
        user.setOriUsername(data.get("username"));
        user.setName(data.get("name"));
        String passwordHash = generateHash(data.get("username"), data.get("confirmPassword"));
        user.setPasswordHash(passwordHash);

        Map<String, String> message = new HashMap<>();
        try {
            userMapper.insertUser(user);
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            message.put("message", "用户名或邮件地址已存在");
            message.put("type", "info");
            return message;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            message.put("message", "请输入必选项");
            message.put("type", "info");
            return message;
        }


        Integer currentUserId = null;
        try {
            currentUserId = userMapper.selectUserIdByUsername(data.get("username"));
            List<String> avatars = generateAvatar.generateAvatar(data.get("username"));
            userMapper.updateCropAvatar(currentUserId, avatars.get(0), avatars.get(1), avatars.get(2));
        } catch (IOException e) {
            e.printStackTrace();
            message.put("message", "注册成功，请上传自定义头像");
            message.put("type", "info");
            return message;
        } catch (NullPointerException e) {
            e.printStackTrace();
            message.put("message", "注册失败，请稍后再试");
            message.put("type", "warning");
            return message;
        }


        String token = JWTToken.createJWT("confirm", currentUserId, null);        String requestUrl = request.getScheme() + "://" + request.getServerName();
        String content = "<p>" + data.get("username") + ", 你好</p>\n" +
                "<p>欢迎您加入<b>分相</b>！</p>\n" +
                "<p>请访问下面链接进行邮箱地址的确认操作:<br>\n" +
                "    <a href=\"" + requestUrl + "/confirm/" + token + "\">" + requestUrl + "/confirm/" + token + "</a>\n" +
                "</p>\n" +
                "<small>(请勿回复此邮件)</small>";

        Map<String, Object> mailMessage = new HashMap<>();
        mailMessage.put("content", content);
        mailMessage.put("subject", "[分相]电子邮件确认");
        mailMessage.put("email", data.get("email"));
        amqpTemplate.convertAndSend("mail", mailMessage);


//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    sendEmail(content, "[分相]电子邮件确认", data.get("email"));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();



        message.put("message", "注册成功，请登录邮箱进行激活以获取更多权限");
        message.put("type", "success");
        return message;
    }
}
