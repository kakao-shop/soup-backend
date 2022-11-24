package com.kcs.soup.api.search.service;

import com.kcs.soup.api.search.document.Product;
import com.kcs.soup.api.search.document.SelectItemLog;
import com.kcs.soup.api.search.dto.RankDto;
import com.kcs.soup.api.search.repository.KeywordRepository;
import com.kcs.soup.api.search.repository.ProductRepository;
import com.kcs.soup.api.search.repository.SelectItemLogRepository;
import com.kcs.soup.common.config.ElasitcConfig;
import com.kcs.soup.common.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecommendService {
    private final JwtTokenProvider jwtTokenProvider;
    private final ProductRepository productRepository;
    private final KeywordRepository keywordRepository;
    private final SelectItemLogRepository selectItemLogRepository;
    private final ElasitcConfig elasitcConfig;

    public List<RankDto> getTop10KeywordRank() throws IOException {
        TermsAggregationBuilder aggregation = AggregationBuilders
                .terms("test")
                .field("keyword.keyword").size(10);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .aggregation(aggregation)
                .sort("count",SortOrder.DESC);
        SearchRequest searchRequest = new SearchRequest("keywordlogs")
                .source(searchSourceBuilder);

        SearchResponse searchResponse = elasitcConfig.client().search(searchRequest, RequestOptions.DEFAULT);
        Iterator<Aggregation> iterator = searchResponse.getAggregations().iterator();
        Terms terms = searchResponse.getAggregations().get("test");
        List<RankDto> rankDtoList = new ArrayList<>();
        for (Terms.Bucket bucket : terms.getBuckets()) {
            String key = bucket.getKey().toString();
            Long count = bucket.getDocCount();
            RankDto rankDto = new RankDto(key, count);
            rankDtoList.add(rankDto);
        }
        return rankDtoList;
    }


    public List<Product> getRecommendItemByMemberidInItemAccessLog(Long memberidx) {
        Sort sort = sortByRecent();
        List<SelectItemLog> logList = selectItemLogRepository.findTop10ByMemberidx(memberidx, sort);
        // 사용자의 선택한 아이템들
        HashMap<String, Integer> subcatMap = getSubcat(logList);
        System.out.println(logList.get(0).getClass());
        if (logList.size() == 0) {
            return getRecommendWithoutLogs();
        }
        List<String> subCatList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();

        for (SelectItemLog  log: logList) {
            if (subCatList.contains(log.getSubcat())) {
                Integer idx = subCatList.indexOf(log.getSubcat());
                countList.set(idx, countList.get(idx) + 1);
            } else {
                subCatList.add(log.getSubcat());
                countList.add(1);
            }
        }
        // 가중치 계산
        List<Integer> weigthList = getWeight(countList);
        int cnt = 0;
        for (String key : subcatMap.keySet()) {
            subcatMap.replace(key, weigthList.get(cnt));
            cnt +=1;
        }
        // 서브 카테고리 / 개수 검색
        List<Product> productList = getProductList(subcatMap);
        return productList;
    }

    private List<Product> getRecommendWithoutLogs() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("score").descending());
        return productRepository.findAll(pageable).toList();
    }

    private HashMap<String, Integer> getSubcat(List<SelectItemLog> logList) {
        HashMap<String, Integer> map = new HashMap<>();
        for (SelectItemLog key : logList) {
            Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "purchase"));
            List<Product> productList = productRepository.findByWebUrl(key.getItemurl(), pageable).get().collect(Collectors.toList());
            String subcat = productList.get(0).getSubcat();
            if (productList.size() > 0) {
                if (map.containsKey(subcat)) {
                    map.replace(subcat, map.get(subcat) +1 );
                } else {
                    map.put(productList.get(0).getSubcat(),1);
                }
            }
        }
        return map;
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


    private List<Integer> getWeight(List<Integer> countList) {
        Integer tmp = countList.stream().mapToInt(Integer::intValue).sum();
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
                countList.set(cnt % countList.size(), countList.get(cnt % countList.size())+1);
            }
            cnt += 1;
        }
        weightList.stream().forEach(System.out::println);

        return weightList;
    }

    private Sort sortByRecent() {
        return Sort.by(Sort.Direction.DESC, "updateat");
    }

}
