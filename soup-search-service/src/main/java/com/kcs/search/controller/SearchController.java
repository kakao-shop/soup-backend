package com.kcs.search.controller;

import com.kcs.search.document.Product;
import com.kcs.search.document.SelectItemLog;
import com.kcs.search.dto.RankDto;
import com.kcs.search.dto.response.RankResponse;
import com.kcs.search.dto.response.RecommendResponse;
import com.kcs.search.dto.response.SearchResponse;
import com.kcs.search.dto.response.SelectItemResponse;
import com.kcs.search.service.CollectionService;
import com.kcs.search.service.RecommendService;
import com.kcs.search.service.SearchService;
import com.kcs.common.dto.BaseResponse;
import com.kcs.common.entity.mysql.Theme;
import com.kcs.common.jwt.JwtTokenProvider;
import com.kcs.common.util.TokenMemberIdx;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequestMapping("/search")
@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;
    private final JwtTokenProvider jwtTokenProvider;

    private final CollectionService collectionService;
    private final RecommendService recommendService;

    @GetMapping("/recent")
    public ResponseEntity<BaseResponse> getRecentlySearchItem(@TokenMemberIdx Long memberidx) {
        List<SelectItemLog> result = recommendService.getSelectItemTop10RecentlyByMemberidx(memberidx);
        return ResponseEntity.ok(new BaseResponse(200, "성공", new SelectItemResponse("recently", result)));

    }

    @GetMapping("/rank")
    public ResponseEntity<BaseResponse> getTop10RealtimeSearchTerm() throws IOException {
        List<RankDto> top10KeywordRank = recommendService.getTop10KeywordRank();
        return ResponseEntity.ok(new BaseResponse(200, "성공", new RankResponse(top10KeywordRank, "rank")));
    }

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
        List<Product> productList = recommendService.getRecommendItemByMemberidInItemAccessLog(jwtTokenProvider.getMemberIdx());
        return ResponseEntity.ok(new BaseResponse(200, "성공", new RecommendResponse("recommend", productList)));
    }


    @GetMapping("/maincat")
    public ResponseEntity<BaseResponse> searchByMaincat(@RequestParam(name = "category") String maincat,
                                                        @PageableDefault(size = 10, sort = "purchase", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Product> result = collectionService.searchByMaincat(maincat, pageable);
        return ResponseEntity.ok(new BaseResponse(200, "성공", new SearchResponse(maincat, result)));
    }

    @PostMapping("/select/item")
    public void saveSelectItemController(@RequestBody Product product) {
        boolean isUserBest = searchService.isUserLogin();
        Long memberidx;
        if (isUserBest) {
            memberidx = jwtTokenProvider.getMemberIdx();
            searchService.updateSelectItemLog(product, memberidx);
        }
    }

    @GetMapping("/subcat")
    public ResponseEntity<BaseResponse> searchBySubcat(@RequestParam(name = "category") String subcat,
                                                       @PageableDefault(size = 10, sort = "purchase", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Product> result = collectionService.searchBySubcat(subcat, pageable);
        return ResponseEntity.ok(new BaseResponse(200, "성공", new SearchResponse(subcat, result)));
    }

    @GetMapping("/collections/{themeIdx}")
    public ResponseEntity<BaseResponse> searchTheme(@PathVariable("themeIdx") Long themeIdx,
                                                    @PageableDefault(size = 10, sort = "score", direction = Sort.Direction.DESC) Pageable defaultPageable) {
        Theme theme = collectionService.findTitleByIdx(themeIdx);
        List<Product> prdList = new ArrayList<>();
        for (String category : theme.getCatList()) {
            prdList.addAll(collectionService.searchAllBySubcat(category));
        }
        Collections.sort(prdList, Collections.reverseOrder());
        final int start = (int) defaultPageable.getOffset();
        final int end = Math.min((start + defaultPageable.getPageSize()), prdList.size());
        final Page<Product> result = new PageImpl<>(prdList.subList(start, end), defaultPageable, prdList.size());
        return ResponseEntity.ok(new BaseResponse(200, "성공", new SearchResponse(theme.getTitle(), result)));
    }

    @GetMapping("/collections/today-best")
    public ResponseEntity<BaseResponse> searchTodayBest() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("score").descending());
        List<Product> result = collectionService.findAll(pageable);
        return ResponseEntity.ok(new BaseResponse(200, "성공", result));
    }

}
