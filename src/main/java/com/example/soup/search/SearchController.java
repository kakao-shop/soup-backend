package com.example.soup.search;

import com.example.soup.common.dto.BaseResponse;
import com.example.soup.search.dto.SubcatSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/search")
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/{productId}")
    public ResponseEntity<BaseResponse> searchWebURL(@PathVariable("productId") String productId) {
        String webURL = searchService.searchWebURL(productId);
        return ResponseEntity.ok(new BaseResponse(200, "성공", webURL));
    }

    @GetMapping()
    public ResponseEntity<BaseResponse> searchSubcat(@RequestParam(name = "category") String subcat,
                                                     @RequestParam(defaultValue = "30") int limit,
                                                     @RequestParam(defaultValue = "best-seller") String type,
                                                     @RequestParam(defaultValue = "0") int page) {
        Sort sort;
        if (type.equals("low-price")) {
            sort = Sort.by("price").descending();
        } else if (type.equals("high-price")) {
            sort = Sort.by("price").ascending();
        } else {
            sort = Sort.by("purchase").descending();
        }
        Pageable pageable = PageRequest.of(page, limit, sort);
        SubcatSearchResponse result = new SubcatSearchResponse(searchService.searchSubcat(subcat, pageable));
        return ResponseEntity.ok(new BaseResponse(200, "성공", result));
    }
}
