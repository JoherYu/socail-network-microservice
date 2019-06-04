package com.sharePhoto.dataFaker.dao;

import com.sharePhoto.common.service.entity.Photo;
import org.springframework.stereotype.Repository;

@Repository("photo")
public interface PhotoMapper {
    // 插入虚拟图片数据
    int insertFake(Photo record);
}