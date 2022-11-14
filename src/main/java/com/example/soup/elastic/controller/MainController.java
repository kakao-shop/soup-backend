package com.example.soup.elastic.controller;

import com.example.soup.api.entity.mariadb.Theme;
import com.example.soup.api.member.jwt.JwtTokenProvider;
import com.example.soup.common.dto.BaseResponse;
import com.example.soup.elastic.document.Product;
import com.example.soup.elastic.dto.MainSearchRespsonse;
import com.example.soup.elastic.service.CollectionService;
import com.example.soup.elastic.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
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
}
