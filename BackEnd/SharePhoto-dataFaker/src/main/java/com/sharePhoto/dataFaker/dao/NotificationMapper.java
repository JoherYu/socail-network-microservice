package com.sharePhoto.dataFaker.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("notification")
public interface NotificationMapper {
    // 增加新消息
    int insert(@Param("message") String message, @Param("receiverId") Integer receiverId);
}