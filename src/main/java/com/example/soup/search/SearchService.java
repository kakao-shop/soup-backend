package com.example.soup.search;

import com.example.soup.admin.ThemeCategoryRepository;
import com.example.soup.admin.ThemeRepository;
import com.example.soup.common.exceptions.ElasticSearchException;
import com.example.soup.common.exceptions.NoSuchThemeExistException;
import com.example.soup.domain.entity.mariadb.ThemeCategory;
import com.example.soup.elasticsearch.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private final SearchRepository searchRepository;

    private final ThemeRepository themeRepository;

    private final ThemeCategoryRepository themeCatRepository;

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


    public List<Product> searchAllBySubcat(String subcat) {
        return searchRepository.findBySubcat(subcat);
    }

    public String findTitleByIdx(Long themeIdx) {
        return themeRepository.findById(themeIdx)
                .orElseThrow(NoSuchThemeExistException::new)
                .getTitle();
    }
}
