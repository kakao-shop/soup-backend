package com.kcs.soup.elastic.controller;

import com.kcs.soup.api.entity.mariadb.Theme;
import com.kcs.soup.common.dto.BaseResponse;
import com.kcs.soup.common.jwt.JwtTokenProvider;
import com.kcs.soup.elastic.document.Product;
import com.kcs.soup.elastic.dto.BotThemeListResponse;
import com.kcs.soup.elastic.dto.MainSearchRespsonse;
import com.kcs.soup.elastic.service.CollectionService;
import com.kcs.soup.elastic.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final JwtTokenProvider jwtTokenProvider;

    private final CollectionService collectionService;

    private final SearchService searchService;


    @GetMapping("/search/main")
    public ResponseEntity<BaseResponse> searchMain() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        List<Theme> themeList = collectionService.findThemeList(pageable);
        boolean isUserBest = searchService.isUserDataExist();
        List<Product> prdList;
        if (isUserBest) {
            Long memberIdx = jwtTokenProvider.getMemberIdx();
            prdList = searchService.getRecommendItemByMemberid(memberIdx);
        } else {
            pageable = PageRequest.of(0, 10, Sort.by("score").descending());
            prdList = collectionService.findAll(pageable);
        }
        MainSearchRespsonse result = new MainSearchRespsonse(themeList, isUserBest, prdList);
        return ResponseEntity.ok(new BaseResponse(200, "성공", result));
    }

    @GetMapping("/bot/collections")
    public ResponseEntity<BaseResponse> findCollections() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        List<BotThemeListResponse> themeList = collectionService.findBotThemeList(pageable);
        return ResponseEntity.ok(new BaseResponse(200, "성공", themeList));
    }
}
