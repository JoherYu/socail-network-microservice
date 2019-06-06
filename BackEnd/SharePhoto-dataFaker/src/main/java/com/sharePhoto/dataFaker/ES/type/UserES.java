package com.sharePhoto.dataFaker.ES.type;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author Joher
 * @data 2019/6/6
 **/

@Document(indexName = "user", type = "user")
public class UserES implements Serializable {

    @Id
    private Integer id;

    @Field(type = FieldType.Keyword)
    private String name;

    @Field(type = FieldType.Keyword)
    private String username;

    @Field(type = FieldType.Keyword, index = false)
    private String avatarM;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarM() {
        return avatarM;
    }

    public void setAvatarM(String avatarM) {
        this.avatarM = avatarM;
    }
}
