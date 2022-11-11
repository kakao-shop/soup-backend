package com.example.soup.elastic.search;

import com.example.soup.domain.entity.elasticsearch.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SearchRepository extends ElasticsearchRepository<Product, String>
{
     Page<Product> findBySubcat(String subcat, Pageable pageable);

     List<Product> findBySubcat(String subcat);
}
