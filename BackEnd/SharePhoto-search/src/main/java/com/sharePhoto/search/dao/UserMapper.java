package com.sharePhoto.search.dao;


import com.sharePhoto.search.ES.type.UserES;
import org.apache.ibatis.annotations.Param;


public interface UserMapper {
    // 当前用户是否关注此用户
    boolean selectUserIsFollowBy(@Param("id") Integer id, @Param("currentUserId") Integer currentUserId);

    // 当前用户是否被此用户关注
    boolean selectUserIsFollowing(@Param("currentUserId") Integer currentUserId, @Param("id") Integer id);

    // 查询新UserDocument
    UserES selectUserDocument(Integer id);
}