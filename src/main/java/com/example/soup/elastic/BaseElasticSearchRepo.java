package com.example.soup.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.List;

@EnableElasticsearchRepositories
public interface BaseElasticSearchRepo extends ElasticsearchRepository<ProductDocs,String> {

}
