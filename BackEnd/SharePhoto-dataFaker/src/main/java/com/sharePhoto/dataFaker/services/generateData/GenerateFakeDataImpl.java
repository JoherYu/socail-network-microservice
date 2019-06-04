package com.sharePhoto.dataFaker.services.generateData;

import com.github.javafaker.Faker;
import com.sharePhoto.common.service.entity.Photo;
import com.sharePhoto.common.service.entity.User;
import com.sharePhoto.dataFaker.dao.*;
import com.sharePhoto.dataFaker.entity.Comment;
import com.sharePhoto.dataFaker.entity.Tagging;
import com.sharePhoto.dataFaker.services.generateAvatar.GenerateAvatar;
import com.sharePhoto.dataFaker.services.generateImage.GenerateImage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.sharePhoto.common.GeneratorPassword.generateHash;

/**
 * @author Joher
 * @data 2019/5/23
 **/
@Service("faker")
public class GenerateFakeDataImpl implements GenerateFakeData, Serializable {

    @Resource
    UserMapper userMapper;

    @Resource
    FollowMapper followMapper;

    @Resource
    TagMapper tagMapper;

    @Resource
    TaggingMapper taggingMapper;

    @Resource
    CollectMapper collectMapper;

    @Resource
    CommentMapper commentMapper;

    @Resource
    NotificationMapper notificationMapper;

    @Resource
    PhotoMapper photoMapper;

    @Resource
    GenerateImage generateImage;

    @Resource
    GenerateAvatar generateAvatar;

    @Override
    @Transactional
    public List<String> fakingIt(){

        List<String> message = new ArrayList<>();

        Faker faker = new Faker();


        User admin = new User();
        admin.setId(1);
        admin.setUsername("admin");
        admin.setOriUsername("admin");
        Integer roleId = 4;
        admin.setRoleId(roleId.byteValue());
        String adminPasswordHash = generateHash("admin", "1234567890");
        admin.setPasswordHash(adminPasswordHash);
        admin.setName("admin");
        admin.setConfirmed(true);
        admin.setBio(faker.shakespeare().romeoAndJulietQuote());
        admin.setLocation(faker.address().city());
        admin.setEmail("joheryu@163.com");try { userMapper.insertFake(admin);
            Integer adminUserId = userMapper.selectUserIdByUsername("admin");
            notificationMapper.insert("欢迎加入分相", adminUserId);
            List<String> adminAvatars = generateAvatar.generateAvatar("admin");
            userMapper.updateCropAvatar(adminUserId, adminAvatars.get(0), adminAvatars.get(1), adminAvatars.get(2));
            userMapper.updateAvatarRaw(adminAvatars.get(2) ,adminUserId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i++){
            User user = new User();
            user.setId(i + 2);
            String username = faker.name().firstName();
            String passwordHash = generateHash(username, "123456");

            user.setUsername(username);
            user.setOriUsername(username);
            user.setPasswordHash(passwordHash);
            user.setName(faker.name().name());
            user.setConfirmed(true);
            user.setWebsite("http://" + faker.internet().domainName());
            user.setBio(faker.shakespeare().romeoAndJulietQuote());
            user.setLocation(faker.address().city());
            user.setEmail(faker.internet().emailAddress());
            try {
                userMapper.insertFake(user);
                Integer currentUserId = userMapper.selectUserIdByUsername(username);
                notificationMapper.insert("欢迎加入分相", currentUserId);
                List<String> avatars = generateAvatar.generateAvatar(username);
                userMapper.updateCropAvatar(currentUserId, avatars.get(0), avatars.get(1), avatars.get(2));
                userMapper.updateAvatarRaw(avatars.get(2), currentUserId);
            } catch (Exception e) {
                e.printStackTrace();
                message.add((i + 1) + "号用户生成失败");
            }
        }

        for (int i = 0; i < 30; i++){
            try {
                followMapper.insert((int)(1+Math.random()*11), (int)(1+Math.random()*11));
            } catch (Exception e) {
                e.printStackTrace();
                message.add("第" + (i + 1) + "条关注生成失败");
            }
        }

        for (int i = 0; i < 20; i++){
            try {
                tagMapper.insertFake(i + 1, faker.food().ingredient());
            } catch (Exception e) {
                e.printStackTrace();
                message.add((i + 1) + "号标签生成失败");
            }
        }

        for (int i = 0; i < 30; i++){
            try {
                String fileUrl = generateImage.generateImage();

                Photo photo = new Photo();
                photo.setId(i + 1);
                photo.setFilenameS(fileUrl);
                photo.setFilename(fileUrl);
                photo.setFilenameM(fileUrl);
                photo.setDescription(faker.lorem().paragraph());
                photo.setAuthorId((int)(1+Math.random()*11));
                photoMapper.insertFake(photo);
                for (int j = 0; j < 5; j++){
                    try {
                        Tagging tagging = new Tagging();
                        tagging.setPhotoId(i + 1);
                        tagging.setTagId((int)(1+Math.random()* 20));
                        taggingMapper.insert(tagging);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                message.add((i + 1) + "号图片生成失败");
            }

        }

        for (int i = 0; i < 50; i++){
            try {
                collectMapper.insert((int)(1+Math.random()* 30), (int)(1+Math.random()* 11));
            } catch (Exception e) {
                e.printStackTrace();
                message.add("第" + (i + 1) + "条收藏生成失败");
            }
        }

        for (int i = 0; i < 100; i++){
            Comment comment = new Comment();
            comment.setId(i + 1);
            comment.setPhotoId((int)(1+Math.random()* 30));
            comment.setAuthorId((int)(1+Math.random()* 11));
            comment.setBody(faker.lorem().paragraph());
            try {
                commentMapper.insertSelective(comment);
            } catch (Exception e) {
                e.printStackTrace();
                message.add((i + 1) + "号评论生成失败");
            }
        }
        return message;
    }



}
