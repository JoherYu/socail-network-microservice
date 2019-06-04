package com.sharePhoto.dataFaker.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("follow")
public interface FollowMapper {
    // 添加关注记录
    int insert(@Param("userId") Integer userId, @Param("currentUserId") Integer currentUserId);
}