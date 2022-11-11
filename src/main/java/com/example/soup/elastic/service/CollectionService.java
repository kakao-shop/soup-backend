package com.example.soup.elastic.service;

import com.example.soup.api.admin.ThemeCategoryRepository;
import com.example.soup.api.admin.ThemeRepository;
import com.example.soup.common.exceptions.NoSuchThemeExistException;
import com.example.soup.api.entity.mariadb.ThemeCategory;
import com.example.soup.elastic.document.Product;
import com.example.soup.elastic.repository.ProductRepository;
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
public class CollectionService {

    private final ProductRepository productRepository;

    private final ThemeRepository themeRepository;

    private final ThemeCategoryRepository themeCatRepository;



    public Page<Product> searchBySubcat(String subcat, Pageable pageable) {
        return productRepository.findBySubcat(subcat, pageable);
    }

    public List<String> findByThemeIdx(Long themeIdx) {
        return themeCatRepository.findByThemeIdx(themeIdx)
                .stream()
                .map(ThemeCategory::getSubCategory)
                .collect(Collectors.toList());
    }


    public List<Product> searchAllBySubcat(String subcat) {
        return productRepository.findBySubcat(subcat);
    }

    public String findTitleByIdx(Long themeIdx) {
        return themeRepository.findById(themeIdx)
                .orElseThrow(NoSuchThemeExistException::new)
                .getTitle();
    }

    public List<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).toList();
    }
}
