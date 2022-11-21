package com.kcs.soup.api.search.service;

import com.kcs.soup.api.admin.reporitory.ThemeRepository;
import com.kcs.soup.api.search.document.Product;
import com.kcs.soup.api.search.dto.BotThemeListResponse;
import com.kcs.soup.api.search.repository.ProductRepository;
import com.kcs.soup.entity.mysql.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
                .map(Theme::toBotThemeListDto)
                .collect(Collectors.toList());
    }

    public List<Product> findTop10BySite(String site, Sort sort) {
        return productRepository.findTop10BySite(site, sort);
    }
}
