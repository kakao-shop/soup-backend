package com.kcs.soup.api.search.service;

import com.kcs.soup.api.admin.ThemeCategoryRepository;
import com.kcs.soup.api.admin.ThemeRepository;
import com.kcs.soup.api.search.document.Product;
import com.kcs.soup.api.search.repository.ProductRepository;
import com.kcs.soup.common.exceptions.NoSuchThemeExistException;
import com.kcs.soup.entity.mysql.Theme;
import com.kcs.soup.entity.mysql.ThemeCategory;
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

    public Page<Product> searchByMaincat(String maincat, Pageable pageable) {
        return productRepository.findByCat(maincat, pageable);
    }

    public List<Theme> findThemeList(Pageable pageable) {
        return themeRepository.findAll(pageable).toList();
    }

}
