package com.sharePhoto.dataFaker.dao;

import com.sharePhoto.common.service.entity.Photo;
import com.sharePhoto.dataFaker.ES.type.PhotoES;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("photo")
public interface PhotoMapper {
    // 插入虚拟图片数据
    int insertFake(Photo record);

    // 查询ES document
    List<PhotoES> selectPhotoDocument();

    // 查询收藏者数量
    Integer selectCollectorCount(Integer photoId);

    // 查询评论数量
    Integer selectCommentCount(Integer photoId);
}