package com.example.soup.elastic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


@EnableElasticsearchRepositories
public interface ProductRepository
        extends ElasticsearchRepository<Product,String>,
        BaseElasticSearchRepository<Product>{
    Page<Product> findByPrdName(String name, Pageable pageable);

}
