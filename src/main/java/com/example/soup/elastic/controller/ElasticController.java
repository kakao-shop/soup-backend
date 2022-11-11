package com.example.soup.elastic.controller;

import com.example.soup.domain.common.dto.BaseResponse;
import com.example.soup.elastic.document.Product;
import com.example.soup.elastic.service.SearchService;
import com.example.soup.elastic.dto.RecommendResponse;
import com.example.soup.elastic.dto.SearchResponse;
import com.example.soup.domain.member.jwt.JwtTokenProvider;
import com.example.soup.elastic.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/search")
@RestController
@RequiredArgsConstructor
public class ElasticController {
    private final ProductRepository BaseElasticSearchRepo;
    private final SearchService searchService;
    private final JwtTokenProvider jwtTokenProvider;
    @GetMapping()
    public ResponseEntity<BaseResponse> testControl(
            @RequestParam(name ="q") String prdname,
            @PageableDefault(size = 10, sort = "purchase", direction = Sort.Direction.DESC) Pageable pageable) {
        List<Product> productList = new ArrayList<>();
        Long memberIDX = jwtTokenProvider.getMemberIdx();
        Page<Product> result = searchService.getProductPage(prdname, pageable, memberIDX);

        for (Product prod: result) {
            System.out.println(prod.getPrdName());
        }
        return ResponseEntity.ok(new BaseResponse(200, "성공", new SearchResponse(prdname, result)));
    }

    @GetMapping("/user-best")
    public ResponseEntity<BaseResponse> getRecommendItemList() {
        List<Product> productList = searchService.getRecommendItemByMemberid(jwtTokenProvider.getMemberIdx());
        return ResponseEntity.ok(new BaseResponse(200, "성공", new RecommendResponse("recommend", productList)));
    }
}
