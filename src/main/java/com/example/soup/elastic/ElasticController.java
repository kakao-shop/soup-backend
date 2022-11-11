package com.example.soup.elastic;

import com.example.soup.domain.common.dto.BaseResponse;
import com.example.soup.domain.entity.elasticsearch.KeywordLog;
import com.example.soup.domain.entity.elasticsearch.Product;
import com.example.soup.elastic.dto.RecommendResponse;
import com.example.soup.elastic.dto.SearchResponse;
import com.example.soup.domain.member.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/kjh")
@RestController
@RequiredArgsConstructor
public class ElasticController {
    private final ProductRepository BaseElasticSearchRepo;
    private final ElasticSearchService searchService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/test")
    public ResponseEntity<BaseResponse> testControl(@RequestParam(name = "prdname") String prdname, @PageableDefault(size = 10, sort = "purchase", direction = Sort.Direction.DESC) Pageable pageable) {
        List<Product> productList = new ArrayList<>();
        Long memberIDX = jwtTokenProvider.getMemberIdx();
        Page<Product> result = searchService.getProductPage(prdname, pageable, memberIDX);

        for (Product prod : result) {
            System.out.println(prod.getPrdName());
        }
        return ResponseEntity.ok(new BaseResponse(200, "성공", new SearchResponse(prdname, result)));
    }

    @GetMapping("/recommend")
    public ResponseEntity<BaseResponse> getRecommendItemList() {
        List<KeywordLog> productList = searchService.getRecommendItemByMemberid(jwtTokenProvider.getMemberIdx());
        return ResponseEntity.ok(new BaseResponse(200, "성공", new RecommendResponse("recommend", productList)));
    }
}
