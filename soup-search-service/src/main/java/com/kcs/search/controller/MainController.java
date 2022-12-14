package com.kcs.search.controller;

import com.kcs.search.document.Product;
import com.kcs.search.dto.response.MainSearchRespsonse;
import com.kcs.search.dto.response.MainThemeResponse;
import com.kcs.search.service.CollectionService;
import com.kcs.search.service.RecommendService;
import com.kcs.search.service.SearchService;
import com.kcs.common.dto.BaseResponse;
import com.kcs.common.jwt.JwtTokenProvider;
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
    private final RecommendService recommendService;
    private final SearchService searchService;


    @GetMapping("/search/main")
    public ResponseEntity<BaseResponse> searchMain() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("idx").descending());
        List<MainThemeResponse> themeList = collectionService.findThemeList(pageable);
        boolean isUserBest = searchService.isUserDataExist();
        List<Product> prdList;
        if (isUserBest) {
            Long memberIdx = jwtTokenProvider.getMemberIdx();
            prdList = recommendService.getRecommendItemByMemberidInItemAccessLog(memberIdx);
        } else {
            pageable = PageRequest.of(0, 10, Sort.by("score").descending());
            prdList = collectionService.findAll(pageable);
        }
        MainSearchRespsonse result = new MainSearchRespsonse(themeList, isUserBest, prdList);
        return ResponseEntity.ok(new BaseResponse(200, "성공", result));
    }
}
