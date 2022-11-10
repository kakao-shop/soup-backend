package com.example.soup.search;

import com.example.soup.common.exceptions.ElasticSearchException;
import com.example.soup.domain.entity.mariadb.ThemeCategory;
import com.example.soup.elasticsearch.Product;
import com.example.soup.search.dto.SearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;

    private final ThemeCatRepository themeCatRepository;

    public String searchByWebURL(String productId) {
        Product findProduct = searchRepository.findById(productId)
                .orElseThrow(ElasticSearchException::new);
        return findProduct.getWebUrl();
    }


    public Page<Product> searchBySubcat(String subcat, Pageable pageable) {
        return searchRepository.findBySubcat(subcat, pageable);
    }

    public List<String> findByThemeIdx(Long themeIdx) {
        return themeCatRepository.findByThemeIdx(themeIdx)
                .stream()
                .map(ThemeCategory::getSubCategory)
                .collect(Collectors.toList());
    }


    public Page<Product> searchAll(Pageable pageable) {
        return searchRepository.findAll(pageable);
    }

    public List<Product> searchAllBySubcat(String subcat) {
        return searchRepository.findBySubcat(subcat);
    }
}
