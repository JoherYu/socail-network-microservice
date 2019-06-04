package com.sharePhoto.dataFaker.dao;

import com.sharePhoto.dataFaker.entity.Comment;
import org.springframework.stereotype.Repository;

@Repository("comment")
public interface CommentMapper {
    // 添加评论记录
    int insertSelective(Comment record);
}