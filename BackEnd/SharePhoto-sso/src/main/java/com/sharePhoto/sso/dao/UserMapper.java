package com.sharePhoto.sso.dao;


import com.sharePhoto.common.service.entity.User;


public interface UserMapper {
    // 根据电子邮箱查询用户名
    String selectUsernameByEmail(String email);

    // 查询用户（登录返回）信息
    User selectLoginUserInfoByEmail(String email);

    // 查询初始用户名
    String selectOriUsernaem(String username);

    // 查询用户登录信息
    User selectLoginInfo(String email);

    // 查询用户是否是Admin权限
    boolean selectIsAdmin(Integer userId);

    // 查询用户未读消息数量
    Integer selectNotificationById(Integer userId);

    // 查询用户是否具有moderate权限
    boolean selectCanModerate(Integer userId);

    // 查询用户是否具有comment权限
    boolean selectCanComment(Integer userId);
}