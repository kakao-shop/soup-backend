package com.kcs.search.service;

import com.kcs.search.document.Product;
import com.kcs.search.dto.response.BotThemeListResponse;
import com.kcs.search.repository.ProductRepository;
import com.kcs.search.repository.ThemeRepository;
import common.entity.mysql.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BotService {

    private final ThemeRepository themeRepository;

    private final ProductRepository productRepository;

    public List<BotThemeListResponse> findThemeList(Pageable pageable) {
        return themeRepository.findAll(pageable)
                .stream()
                .map(BotThemeListResponse::from)
                .collect(Collectors.toList());
    }

    public Page<Product> findBySite(String site, Pageable pageable) {
        return productRepository.findBySite(site, pageable);
    }
}
