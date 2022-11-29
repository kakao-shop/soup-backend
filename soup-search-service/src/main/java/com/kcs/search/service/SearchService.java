package com.kcs.search.service;


import com.kcs.search.document.KeywordLog;
import com.kcs.search.document.Product;
import com.kcs.search.document.SelectItemLog;
import com.kcs.search.repository.elasticsearch.KeywordRepository;
import com.kcs.search.repository.elasticsearch.ProductRepository;
import com.kcs.search.repository.elasticsearch.SelectItemLogRepository;
import com.kcs.common.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final SelectItemLogRepository selectItemLogRepository;

    public void updateSelectItemLog(Product product, Long memberidx) {
        SelectItemLog selectItemLog = selectItemLogRepository.findByMemberidxAndWebUrl(memberidx, product.getWebUrl());
        saveSelectItemLog(product, memberidx, selectItemLog);
    }


    private void saveSelectItemLog(Product product, Long memberidx, SelectItemLog selectItemLog) {
        if (selectItemLog != null) {
            selectItemLog.setCount(selectItemLog.getCount() + 1);
            selectItemLog.updateTime();
            selectItemLogRepository.save(selectItemLog);
        } else {
            selectItemLogRepository.save(SelectItemLog.builder()
                    .pid(product.getId())
                    .webUrl(product.getWebUrl())
                    .cat(product.getCat())
                    .prdName(product.getPrdName())
                    .imgSrc(product.getImgSrc())
                    .score(product.getScore())
                    .site(product.getSite())
                    .subcat(product.getSubcat())
                    .purchase(product.getPurchase())
                    .price(product.getPrice())

                    .memberidx(memberidx)
                    .updateat(LocalDateTime.now())
                    .count(1)
                    .build());
        }
    }

    public Page<Product> getProductPage(String prdname, Pageable pageable, Long memberidx) {
        if (memberidx != null) {
            saveKeywordLog(prdname, memberidx);
        } else {
            saveKeywordLog(prdname, null);
        }

        return productRepository.findByPrdName(prdname, pageable);
    }


    public List<Product> getRecommendItemByMemberidInKeywordLog(Long memberidx) {
        Sort sort = sortByCount();
        List<KeywordLog> logList = keywordRepository.findTop10ByMemberidx(memberidx, sort);
        List<KeywordLog> logListHaveSubcat = getKeywordLogHaveSubcat(logList);
        if (logListHaveSubcat.size() == 0) {
            return getRecommendWithoutLogs();
        }

        String[] keyList = new String[logListHaveSubcat.size()];
        int[] countList = new int[logListHaveSubcat.size()];
        for (int i = 0; i < logListHaveSubcat.size(); i++) {
            keyList[i] = logListHaveSubcat.get(i).getKeyword();
            countList[i] = logListHaveSubcat.get(i).getCount();
        }

        List<Integer> weigthList = getWeight(keyList, countList);
        List<String> subCatList = getSubcat(keyList);
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

    private List<Product> getRecommendWithoutLogs() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("score").descending());
        return productRepository.findAll(pageable).toList();
    }

    private List<KeywordLog> getKeywordLogHaveSubcat(List<KeywordLog> logList) {
        List<KeywordLog> keyListHaveSubcat = new ArrayList<>();
        for (KeywordLog key : logList) {
            Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "purchase"));
            List<Product> productList = productRepository.findByPrdName(key.getKeyword(), pageable).get().collect(Collectors.toList());
            if (productList.size() > 0) {
                keyListHaveSubcat.add(key);
            }
        }
        if (keyListHaveSubcat.size() > 3) {
            keyListHaveSubcat = new ArrayList<>(keyListHaveSubcat.subList(0, 3));
        }
        return keyListHaveSubcat;
    }

    private List<Product> getProductList(HashMap<String, Integer> data) {
        List<Product> productList = new ArrayList<>();
        data.forEach((k, v) -> {
            Pageable pageable = PageRequest.of(0, v, Sort.by(Sort.Direction.DESC, "purchase"));
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
            Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "purchase"));
            List<Product> productList = productRepository.findByPrdName(key, pageable).get().collect(Collectors.toList());
            subCatList.add(productList.get(0).getSubcat());
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
                weightList.add(Integer.valueOf(i / tmp));
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


    private void saveKeywordLog(String prdname, Long memberidx) {

        if (memberidx != null) {
            KeywordLog keywordLog = KeywordLog.builder()
                    .keyword(prdname)
                    .count(1).memberidx(memberidx)
                    .updateat(LocalDateTime.now())
                    .build();
            keywordRepository.save(keywordLog);
        } else {
            KeywordLog keywordLog = KeywordLog.builder()
                    .keyword(prdname)
                    .count(1).memberidx(null)
                    .updateat(LocalDateTime.now())
                    .build();
            keywordRepository.save(keywordLog);
        }
    }

    public boolean isUserLogin() {
        Long memberIdx = jwtTokenProvider.getMemberIdxIfLogined();
        return memberIdx != null;
    }

    public boolean isUserDataExist() {
        Long memberIdx = jwtTokenProvider.getMemberIdxIfLogined();
        if (memberIdx == null) {
            return false;
        }
        return true;
    }

}
