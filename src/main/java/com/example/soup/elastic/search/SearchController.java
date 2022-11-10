package com.example.soup.elastic.search;

import com.example.soup.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/search")
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/{productId}")
    public ResponseEntity<BaseResponse> searchWebURL(@PathVariable String productId){
        String webURL = searchService.searchWebURL(productId);
        return ResponseEntity.ok(new BaseResponse(200,"성공",webURL));
    }
}
