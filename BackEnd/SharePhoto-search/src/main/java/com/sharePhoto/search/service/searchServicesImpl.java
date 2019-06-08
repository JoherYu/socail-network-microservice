package com.sharePhoto.search.service;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.sharePhoto.common.CookieUtils;
import com.sharePhoto.common.service.entity.User;
import com.sharePhoto.search.ES.repository.PhotoRepository;
import com.sharePhoto.search.ES.repository.TagRepository;
import com.sharePhoto.search.ES.repository.UserRepository;
import com.sharePhoto.search.ES.type.PhotoES;
import com.sharePhoto.search.ES.type.TagES;
import com.sharePhoto.search.ES.type.UserES;
import com.sharePhoto.search.consumer.RedisServer;
import com.sharePhoto.search.dao.UserMapper;
import com.sharePhoto.search.utils.JsonSerilizable;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Joher
 * @data 2019/6/6
 **/

@Service("search")
public class searchServicesImpl implements searchServices {

    @Resource
    PhotoRepository photoRepository;

    @Resource
    TagRepository tagRepository;

    @Resource
    UserRepository userRepository;

    @Resource
    RedisServer redisServer;

    @Resource
    UserMapper userMapper;

    @Override
    public Map<String, Object> search(String category, String q, Integer page, HttpServletRequest request) {
        if (q.equals("")){
            Map<String,Object> message = new HashMap<>();
            message.put("message", "请输入相片、用户名或标签关键词");
            message.put("type", "warning");
            return message;
        }

        Page<?> result = null;
        List<Object> resultContent = new ArrayList<>();
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        Pageable pageable = PageRequest.of(page - 1, 20);
        String condition = "*" + q + "*";
        if (category.equals("user")){
            builder.withQuery(QueryBuilders.boolQuery().should(QueryBuilders.wildcardQuery("username",condition)).should(QueryBuilders.wildcardQuery("name",condition))).withPageable(pageable);
            Page<UserES> users = userRepository.search(builder.build());
            result = users;
            String token = CookieUtils.getCookieValue(request, "token");
            if (token == null || redisServer.get(token) == null){
                for (UserES user : users) {
                    User userInfo = new User();
                    userInfo.setId(user.getId());
                    userInfo.setName(user.getName());
                    userInfo.setUsername(user.getUsername());
                    userInfo.setAvatarM(user.getAvatarM());
                    userInfo.setFollowed(false);
                    userInfo.setFollowing(false);
                    resultContent.add(userInfo);
                }
            }else {
                Integer currentUserId = null;
                try {
                    Map<String, Object> cache = (Map<String,Object>) JSONObject.parseObject(redisServer.get(token));
                    currentUserId = (Integer) cache.get("id");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Map<String, Object> message = new HashMap<>();
                    message.put("message", "请先登录");
                    message.put("type", "warning");
                    return message;
                }
                for (UserES user : users) {
                    User userInfo = new User();
                    userInfo.setId(user.getId());
                    userInfo.setName(user.getName());
                    userInfo.setUsername(user.getUsername());
                    userInfo.setAvatarM(user.getAvatarM());
                    try {
                        userInfo.setFollowed(userMapper.selectUserIsFollowBy(user.getId(), currentUserId));
                        userInfo.setFollowing(userMapper.selectUserIsFollowing(currentUserId, user.getId()));
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        Map<String, Object> message = new HashMap<>();
                        message.put("message", "请确认您处于登录状态并检查用户是否存在");
                        message.put("type", "warning");
                        return message;
                    }
                    resultContent.add(userInfo);
                }
            }
        }else if (category.equals("tag")){
            builder.withQuery(QueryBuilders.wildcardQuery("name", condition)).withPageable(pageable);
            Page<TagES> tags = tagRepository.search(builder.build());
            result = tags;
            for (TagES tag : tags) {
                resultContent.add(tag);
            }
        }else {
            builder.withQuery(QueryBuilders.wildcardQuery("description",condition)).withPageable(pageable);
            Page<PhotoES> photos = photoRepository.search(builder.build());
            result = photos;
            for (PhotoES photo : photos) {
                resultContent.add(photo);
            }
        }

        Map<String, Object> pagination = new HashMap<>();
        pagination.put("count", result.getTotalElements());
        pagination.put("currentPage", page);
        pagination.put("perPage", 20);
        pagination.put("items", resultContent);

        return pagination;
    }

    @Override
    @RabbitListener(queues = "es")
    public void insertUser(Message message, Channel channel) throws IOException {
        Map<String, Integer> esMessage = JsonSerilizable.deserilizableForMapFromFile(new String(message.getBody()), Integer.class);
        Integer id = esMessage.get("id");
        UserES user = userMapper.selectUserDocument(id);
        userRepository.save(user);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }
}
