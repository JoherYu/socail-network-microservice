package com.sharePhoto.dataFaker.dao;

import com.sharePhoto.dataFaker.ES.type.TagES;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("tag")
public interface TagMapper {
    // 添加标签
    Integer insertFake(@Param("id") Integer id, @Param("name") String name);

    // 根据ES document
    List<TagES> selectTagDocument();

}