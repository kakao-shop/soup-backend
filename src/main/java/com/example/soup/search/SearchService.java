package com.example.soup.search;

import com.example.soup.common.exceptions.ElasticSearchException;
import com.example.soup.elasticsearch.Product;
import com.example.soup.search.dto.SearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ProductRepository productRepository;

    public String searchWebURL(String productId) {
        Product findProduct = productRepository.findById(productId)
                .orElseThrow(ElasticSearchException::new);
        return findProduct.getWebUrl();
    }

    public List<SearchDto> searchSubcat(String subcat, Pageable pageable) {
        return productRepository.findBySubcat(subcat, pageable)
                .stream()
                .map(SearchDto::from)
                .collect(Collectors.toList());
    }


}
