package com.example.soup.search;

import com.example.soup.common.dto.BaseResponse;
import com.example.soup.elasticsearch.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/search")
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    // 상품 상세 페이지로 이동하는 링크 반환 API
    @GetMapping("/{productId}")
    public ResponseEntity<BaseResponse> searchWebURL(@PathVariable("productId") String productId) {
        String webURL = searchService.searchWebURL(productId);
        return ResponseEntity.ok(new BaseResponse(200, "성공", webURL));
    }

    // 소카테고리 검색
    @GetMapping()
    public ResponseEntity<BaseResponse> searchSubcat(@RequestParam(name = "category") String subcat,
                                                     @RequestParam(defaultValue = "30") int limit,
                                                     @RequestParam(defaultValue = "best-seller") String type,
                                                     @RequestParam(defaultValue = "0") int page) {
        Sort sort;
        if (type.equals("low-price")) {
            sort = Sort.by("price").ascending();
        } else if (type.equals("high-price")) {
            sort = Sort.by("price").descending();
        } else {
            sort = Sort.by("purchase").descending();
        }
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<Product> result = searchService.searchSubcat(subcat, pageable);
        return ResponseEntity.ok(new BaseResponse(200, "성공", result));
    }

    // 테마별 상품 추천
    @GetMapping("/collections/{themeIdx}")
    public ResponseEntity<BaseResponse> searchTheme(@PathVariable("themeIdx") Long themeIdx) {
        List<String> catList = searchService.findByThemeIdx(themeIdx);
        List<Product> result = new ArrayList<>();
        int cnt = catList.size();
        int size = 10 / cnt;
        for (int i = 0; i < cnt; i++) {
            if (i == (cnt - 1)) {
                size += 10 % cnt;
            }
            Pageable pageable = PageRequest.of(0, size, Sort.by("purchase").descending());
            result.addAll(searchService.searchSubcat(catList.get(i), pageable).toList());
        }
        if (result.size() < 10) {
            Pageable pageable = PageRequest.of(0, 10 - result.size(), Sort.by("purchase").descending());
            result.addAll(searchService.searchAll(pageable).toList());
        }
        return ResponseEntity.ok(new BaseResponse(200, "성공", result));
    }
}
