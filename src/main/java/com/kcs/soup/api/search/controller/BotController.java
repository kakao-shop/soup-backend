package com.kcs.soup.api.search.controller;

import com.kcs.soup.api.search.document.Product;
import com.kcs.soup.api.search.dto.BotThemeListResponse;
import com.kcs.soup.api.search.service.BotService;
import com.kcs.soup.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/bot")
@RestController
@RequiredArgsConstructor
public class BotController {

    private final BotService botService;

    @GetMapping("/collections")
    public ResponseEntity<BaseResponse> searchCollections() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        List<BotThemeListResponse> themeList = botService.findThemeList(pageable);
        return ResponseEntity.ok(new BaseResponse(200, "성공", themeList));
    }

    @GetMapping("/today-best")
    public ResponseEntity<BaseResponse> searchTodayBestBySite(
            @RequestParam(name = "site") String site,
            @PageableDefault(size = 10, sort = "purchase", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        List<Product> result = botService.findBySite(site, pageable);
        return ResponseEntity.ok(new BaseResponse(200, "성공", result));
    }

}
