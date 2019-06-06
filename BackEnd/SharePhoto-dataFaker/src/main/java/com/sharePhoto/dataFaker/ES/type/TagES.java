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

@Document(indexName = "tag", type = "tag")
public class TagES implements Serializable {

    @Id
    private Integer id;

    @Field(type = FieldType.Keyword)
    private String name;

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
}
