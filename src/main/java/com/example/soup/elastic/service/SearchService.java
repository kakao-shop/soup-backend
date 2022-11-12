package com.example.soup.elastic.service;


import com.example.soup.api.entity.mariadb.Theme;
import com.example.soup.api.member.jwt.JwtTokenProvider;
import com.example.soup.elastic.document.KeywordLog;
import com.example.soup.elastic.document.Product;
import com.example.soup.elastic.repository.KeywordRepository;
import com.example.soup.elastic.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final JwtTokenProvider jwtTokenProvider;
    private final ProductRepository productRepository;
    private final KeywordRepository keywordRepository;

    public Page<Product> getProductPage(String prdname, Pageable pageable, Long memberidx) {
        if (memberidx != null) {
            System.out.println("로그 저장");
            KeywordLog keywordObject = keywordRepository.findByMemberidxAndKeyword(memberidx, prdname);

            saveKeywordLog(prdname, memberidx, keywordObject);
        }

        return productRepository.findByPrdName(prdname, pageable);
    }

    public List<Product> getRecommendItemByMemberid(Long memberidx) {
        Sort sort = sortByCount();
        List<KeywordLog> logList = keywordRepository.findTop3ByMemberidx(memberidx, sort);

        String[] keyList = new String[logList.size()];
        int[] countList = new int[logList.size()];

        for (int i = 0; i < logList.size(); i++) {
            keyList[i] = logList.get(i).getKeyword();
            countList[i] = logList.get(i).getCount();
        }
        List<Integer> weigthList = getWeight(keyList, countList);
        // 가중치 계산
        List<String> subCatList = getSubcat(keyList);
        // sub cat 구하기

        HashMap<String, Integer> data = new HashMap<>();
        for (int i = 0; i < weigthList.size(); i++) {

            if (data.containsKey(subCatList.get(i))) {
                data.put(subCatList.get(i), data.get(subCatList.get(i)) + weigthList.get(i));
            } else {
                data.put(subCatList.get(i), weigthList.get(i));
            }
        }

        List<Product> productList = getProductList(data);
        return productList;
    }

    private List<Product> getProductList(HashMap<String, Integer> data) {
        List<Product> productList = new ArrayList<>();
        data.forEach((k, v) -> {
            Pageable pageable = PageRequest.of(1, v, Sort.by(Sort.Direction.DESC, "purchase"));
            Page<Product> products = productRepository.findBySubcat(k, pageable);
            for (Product product : products) {
                productList.add(product);
            }
        });

        return productList;
    }

    private List<String> getSubcat(String[] keyList) {
        List<String> subCatList = new ArrayList<>();
        for (String key : keyList) {
            Pageable pageable = PageRequest.of(1, 1, Sort.by(Sort.Direction.DESC, "purchase"));
            Product product = productRepository.findByPrdName(key, pageable).get().collect(Collectors.toList()).get(0);
            subCatList.add(product.getSubcat());
        }
        return subCatList;
    }

    private List<Integer> getWeight(String[] keyList, int[] countList) {
        Integer tmp = Arrays.stream(countList).sum();
        List<Integer> weightList;
        Integer cnt = 0;
        while (true) {
            Integer re = 0;
            weightList = new ArrayList<>();
            for (int i : countList) {
                re += Integer.valueOf(i / tmp);
                weightList.add(Integer.valueOf((i / tmp)));
            }
            if (re == 10) {
                break;
            } else {
                countList[cnt % countList.length] += 1;
            }
            cnt += 1;
        }

        return weightList;
    }

    private Sort sortByCount() {
        return Sort.by(Sort.Direction.DESC, "count");
    }

    private void saveKeywordLog(String prdname, Long memberidx, KeywordLog keywordObject) {
        if (keywordObject != null) {
            keywordObject.setCount(keywordObject.getCount() + 1);
            keywordRepository.save(keywordObject);
        } else {
            KeywordLog keywordLog = KeywordLog.builder()
                    .keyword(prdname)
                    .count(1).memberidx(memberidx)
                    .build();
            keywordRepository.save(keywordLog);
        }
    }
    public boolean isUserLogin() {
        Long memberIdx = jwtTokenProvider.getMemberIdxIfLogined();
        if (memberIdx == null) {
            return false;
        }
        return true;
    }
    public boolean isUserDataExist() {
        Long memberIdx = jwtTokenProvider.getMemberIdxIfLogined();
        if (memberIdx == null) {
            return false;
        }
        return keywordRepository.existsKeywordLogByMemberidx(memberIdx);
    }

}
