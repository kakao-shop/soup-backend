package com.example.soup.search;

import com.example.soup.elasticsearch.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends ElasticsearchRepository<Product, String>
{
     Page<Product> findBySubcat(String subcat, Pageable pageable);
}
