package com.example.soup.elastic.repository;

import com.example.soup.elastic.document.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.List;

@EnableElasticsearchRepositories
public interface ProductRepository
        extends ElasticsearchRepository<Product, String>,
        BaseElasticSearchRepository<Product> {
    Page<Product> findByPrdName(String name, Pageable pageable);

    Page<Product> findBySubcat(String subcat, Pageable pageable);


    List<Product> findBySubcat(String subcat);

    Page<Product> findByCat(String maincat,Pageable pageable);
}
