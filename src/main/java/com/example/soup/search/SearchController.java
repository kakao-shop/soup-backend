package com.example.soup.search;

import com.example.soup.common.dto.BaseResponse;
import com.example.soup.elasticsearch.Product;
import com.example.soup.search.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequestMapping("/search")
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    // 상품 상세 페이지로 이동하는 링크 반환 API
    @GetMapping("/{productId}")
    public ResponseEntity<BaseResponse> searchByWebURL(@PathVariable("productId") String productId) {
        String webURL = searchService.searchByWebURL(productId);
        return ResponseEntity.ok(new BaseResponse(200, "성공", webURL));
    }

    // 소카테고리 검색
    @GetMapping()
    public ResponseEntity<BaseResponse> searchBySubcat(@RequestParam(name = "category") String subcat,
                                                       @PageableDefault(size = 10, sort = "purchase", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Product> result = searchService.searchBySubcat(subcat, pageable);
        return ResponseEntity.ok(new BaseResponse(200, "성공", new SearchResponse(subcat,result)));
    }

    // 테마별 상품 추천
    @GetMapping("/collections/{themeIdx}")
    public ResponseEntity<BaseResponse> searchTheme(@PathVariable("themeIdx") Long themeIdx,
                                                    @PageableDefault(size = 10, sort = "purchase", direction = Sort.Direction.DESC) Pageable defaultPageable) {
        String themeTitle = searchService.findTitleByIdx(themeIdx);
        List<String> catList = searchService.findByThemeIdx(themeIdx);
        List<Product> prdList = new ArrayList<>();
        for (int i = 0; i < catList.size(); i++) {
            prdList.addAll(searchService.searchAllBySubcat(catList.get(i)));
        }
        Collections.sort(prdList, Collections.reverseOrder());
        final int start = (int) defaultPageable.getOffset();
        final int end = Math.min((start + defaultPageable.getPageSize()), prdList.size());
        final Page<Product> result = new PageImpl<>(prdList.subList(start, end), defaultPageable, prdList.size());
        return ResponseEntity.ok(new BaseResponse(200, "성공", new SearchResponse(themeTitle,result)));
    }
}
