package com.sharePhoto.dataFaker.ES.repository;

import com.sharePhoto.dataFaker.ES.type.UserES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @author Joher
 * @data 2019/6/6
 **/
@Component
public interface UserRepository  extends ElasticsearchRepository<UserES, Integer> {
}
