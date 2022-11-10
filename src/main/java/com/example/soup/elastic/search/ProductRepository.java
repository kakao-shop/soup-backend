package com.example.soup.elastic.search;

import com.example.soup.elastic.elasticsearch.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, String>
{
}
