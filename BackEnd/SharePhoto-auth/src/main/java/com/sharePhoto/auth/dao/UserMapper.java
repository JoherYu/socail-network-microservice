package com.sharePhoto.auth.dao;

import com.sharePhoto.common.service.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    // 新增用户
    Integer insertUser(User user);

    // 查询当前用户Id
    Integer selectUserIdByUsername(String username);

    // 更新头像缩略图
    Integer updateCropAvatar(@Param("id") Integer id, @Param("avatarS") String avatarS, @Param("avatarM") String avatarM, @Param("avatarL") String avatarL);

    // 根据用户id查询用户邮件
    String selectEmailById(Integer id);

    // 更新用户确认信息
    Integer updateConfirmById(Integer id);

}