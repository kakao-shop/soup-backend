package com.example.soup.elastic;

import com.example.soup.domain.entity.elasticsearch.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface ProductRepository
        extends ElasticsearchRepository<Product, String>,
        BaseElasticSearchRepository<Product> {
    Page<Product> findByPrdName(String name, Pageable pageable);

}
