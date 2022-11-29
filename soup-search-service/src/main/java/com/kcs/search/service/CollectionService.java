package com.kcs.search.service;

import com.kcs.search.document.Product;
import com.kcs.search.dto.response.MainThemeResponse;
import com.kcs.search.repository.elasticsearch.ProductRepository;
import com.kcs.search.repository.jpa.ThemeRepository;
import com.kcs.common.entity.mysql.Theme;
import com.kcs.common.exception.NoSuchThemeExistException;
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


    public Page<Product> searchBySubcat(String subcat, Pageable pageable) {
        return productRepository.findBySubcat(subcat, pageable);
    }


    public List<Product> searchAllBySubcat(String subcat) {
        return productRepository.findBySubcat(subcat);
    }

    public Theme findTitleByIdx(Long themeIdx) {
        return themeRepository.findById(themeIdx)
                .orElseThrow(NoSuchThemeExistException::new);
    }

    public List<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).toList();
    }

    public Page<Product> searchByMaincat(String maincat, Pageable pageable) {
        return productRepository.findByCat(maincat, pageable);
    }

    public List<MainThemeResponse> findThemeList(Pageable pageable) {
        return themeRepository.findAll(pageable)
                .toList()
                .stream()
                .map(MainThemeResponse::of)
                .collect(Collectors.toList());
    }

}
