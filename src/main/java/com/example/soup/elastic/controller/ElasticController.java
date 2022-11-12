package com.example.soup.elastic.controller;

import com.example.soup.api.member.jwt.JwtTokenProvider;
import com.example.soup.common.dto.BaseResponse;
import com.example.soup.elastic.document.Product;
import com.example.soup.elastic.dto.RecommendResponse;
import com.example.soup.elastic.dto.SearchResponse;
import com.example.soup.elastic.service.CollectionService;
import com.example.soup.elastic.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequestMapping("/search")
@RestController
@RequiredArgsConstructor
public class ElasticController {
    private final SearchService searchService;
    private final JwtTokenProvider jwtTokenProvider;

    private final CollectionService collectionService;

    @GetMapping("")
    public ResponseEntity<BaseResponse> searchQuery(
            @RequestParam(name = "q") String prdname,
            @PageableDefault(size = 10, sort = "purchase", direction = Sort.Direction.DESC) Pageable pageable) {

        boolean isUserBest = searchService.isUserLogin();
        Long memberIDX;
        Page<Product> result;
        if (isUserBest) {
            memberIDX = jwtTokenProvider.getMemberIdx();
            result = searchService.getProductPage(prdname, pageable, memberIDX);
        } else {
            result = searchService.getProductPage(prdname, pageable, null);
        }
        return ResponseEntity.ok(new BaseResponse(200, "성공", new SearchResponse(prdname, result)));
    }

    @GetMapping("/collections/user-best")
    public ResponseEntity<BaseResponse> getRecommendItemList() {
        List<Product> productList = searchService.getRecommendItemByMemberid(jwtTokenProvider.getMemberIdx());
        return ResponseEntity.ok(new BaseResponse(200, "성공", new RecommendResponse("recommend", productList)));
    }

    // 대카테고리 검색
    @GetMapping("/maincat")
    public ResponseEntity<BaseResponse> searchByMaincat(@RequestParam(name = "category") String maincat,
                                                        @PageableDefault(size = 10, sort = "purchase", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Product> result = collectionService.searchByMaincat(maincat,pageable);
        return ResponseEntity.ok(new BaseResponse(200, "성공", new SearchResponse(maincat, result)));
    }

    // 소카테고리 검색
    @GetMapping("/subcat")
    public ResponseEntity<BaseResponse> searchBySubcat(@RequestParam(name = "category") String subcat,
                                                       @PageableDefault(size = 10, sort = "purchase", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Product> result = collectionService.searchBySubcat(subcat, pageable);
        return ResponseEntity.ok(new BaseResponse(200, "성공", new SearchResponse(subcat, result)));
    }

    // 테마별 상품 추천
    @GetMapping("/collections/{themeIdx}")
    public ResponseEntity<BaseResponse> searchTheme(@PathVariable("themeIdx") Long themeIdx,
                                                    @PageableDefault(size = 10, sort = "purchase", direction = Sort.Direction.DESC) Pageable defaultPageable) {
        String themeTitle = collectionService.findTitleByIdx(themeIdx);
        List<String> catList = collectionService.findByThemeIdx(themeIdx);
        List<Product> prdList = new ArrayList<>();
        for (String category : catList) {
            prdList.addAll(collectionService.searchAllBySubcat(category));
        }
        Collections.sort(prdList, Collections.reverseOrder());
        final int start = (int) defaultPageable.getOffset();
        final int end = Math.min((start + defaultPageable.getPageSize()), prdList.size());
        final Page<Product> result = new PageImpl<>(prdList.subList(start, end), defaultPageable, prdList.size());
        return ResponseEntity.ok(new BaseResponse(200, "성공", new SearchResponse(themeTitle, result)));
    }


    // 오늘의 추천
    @GetMapping("/collections/today-best")
    public ResponseEntity<BaseResponse> searchTodayBest() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("score").descending());
        List<Product> result = collectionService.findAll(pageable);
        return ResponseEntity.ok(new BaseResponse(200, "성공", result));
    }

}
