package com.sharePhoto.search.ES.type;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author Joher
 * @data 2019/6/6
 **/

@Document(indexName = "photo", type = "photo")
public class PhotoES implements Serializable {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilenameS() {
        return filenameS;
    }

    public void setFilenameS(String filenameS) {
        this.filenameS = filenameS;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getCollectorCount() {
        return collectorCount;
    }

    public void setCollectorCount(Integer collectorCount) {
        this.collectorCount = collectorCount;
    }

    @Id
    private Integer id;

    @Field(type = FieldType.Keyword, index = false)
    private String filenameS;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String description;

    @Field(type = FieldType.Integer, index = false)
    private Integer commentCount;

    @Field(type = FieldType.Integer, index = false)
    private Integer collectorCount;

}
