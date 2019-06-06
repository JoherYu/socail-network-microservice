package com.sharePhoto.search.ES.repository;


import com.sharePhoto.search.ES.type.PhotoES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @author Joher
 * @data 2019/6/6
 **/
@Component
public interface PhotoRepository extends ElasticsearchRepository<PhotoES, Integer> {
}
