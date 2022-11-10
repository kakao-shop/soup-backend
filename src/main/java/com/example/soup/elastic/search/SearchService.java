package com.example.soup.elastic.search;

import com.example.soup.common.exceptions.ElasticSearchException;
import com.example.soup.elastic.elasticsearch.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ProductRepository productRepository;
    public String searchWebURL(String productId) {
        Product findProduct = productRepository.findById(productId)
                .orElseThrow(ElasticSearchException::new);
        return findProduct.getWebUrl();
    }
}
