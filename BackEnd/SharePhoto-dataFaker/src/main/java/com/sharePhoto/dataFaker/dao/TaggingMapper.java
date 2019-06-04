package com.sharePhoto.dataFaker.dao;

import com.sharePhoto.dataFaker.entity.Tagging;
import org.springframework.stereotype.Repository;

@Repository("tagging")
public interface TaggingMapper {
    // 添加图片标签
    int insert(Tagging record);
}