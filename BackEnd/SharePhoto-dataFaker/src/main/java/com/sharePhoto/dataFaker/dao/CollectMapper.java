package com.sharePhoto.dataFaker.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("collect")
public interface CollectMapper {
    // 添加收藏记录
    int insert(@Param("photoId") Integer photoId, @Param("currentUserId") Integer currentUserId);
}