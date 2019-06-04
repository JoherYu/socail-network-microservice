package com.sharePhoto.dataFaker.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("tag")
public interface TagMapper {
    // 添加标签
    Integer insertFake(@Param("id") Integer id, @Param("name") String name);

}