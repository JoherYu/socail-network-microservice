package com.sharePhoto.dataFaker.dao;

import com.sharePhoto.common.service.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    // 生成虚拟用户
    Integer insertFake(User user);

    // 为虚拟用户添加RAW头像
    Integer updateAvatarRaw(@Param("avatarRaw") String avatarRaw, @Param("id") Integer id);

    // 查询当前用户Id
    Integer selectUserIdByUsername(String username);

    // 更新头像缩略图
    Integer updateCropAvatar(@Param("id") Integer id, @Param("avatarS") String avatarS, @Param("avatarM") String avatarM, @Param("avatarL") String avatarL);
}