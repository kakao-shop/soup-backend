package com.kcs.search.service;

import com.kcs.search.config.ElasitcConfig;
import com.kcs.search.document.Product;
import com.kcs.search.document.SelectItemLog;
import com.kcs.search.dto.RankDto;
import com.kcs.search.repository.elasticsearch.ProductRepository;
import com.kcs.search.repository.elasticsearch.SelectItemLogRepository;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RecommendService {
    private final ProductRepository productRepository;
    private final SelectItemLogRepository selectItemLogRepository;
    private final ElasitcConfig elasitcConfig;


    public List<RankDto> getTop10KeywordRank() throws IOException {
        String startTime = LocalDateTime.now().minusHours(24).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        TermsAggregationBuilder aggregation = AggregationBuilders
                .terms("rank")
                .field("keyword.keyword")
                .size(10);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .filter(QueryBuilders.rangeQuery("updateat")
                        .gte(startTime));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(boolQueryBuilder)
                .aggregation(aggregation)
                .sort("count", SortOrder.DESC);
        SearchRequest searchRequest = new SearchRequest("keywordlogs")
                .source(searchSourceBuilder);
        SearchResponse searchResponse;
        try {
            searchResponse = elasitcConfig.client().search(searchRequest, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            return new ArrayList<RankDto>();
        }

        Terms terms = searchResponse.getAggregations().get("rank");
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

        List<SelectItemLog> logList = getSelectItemTop10RecentlyByMemberidx(memberidx);
        // ???????????? ????????? ????????????
        HashMap<String, Integer> subcatMap = getSubcat(logList);
        if (logList.size() == 0) {
            return getRecommendWithoutLogs();
        }
        List<String> subCatList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();

        for (SelectItemLog log : logList) {
            if (subCatList.contains(log.getSubcat())) {
                Integer idx = subCatList.indexOf(log.getSubcat());
                countList.set(idx, countList.get(idx) + 1);
            } else {
                subCatList.add(log.getSubcat());
                countList.add(1);
            }
        }
        // ????????? ??????
        List<Integer> weigthList = getWeight(countList);
        int cnt = 0;
        for (String key : subcatMap.keySet()) {
            subcatMap.replace(key, weigthList.get(cnt));
            cnt += 1;
        }
        // ?????? ???????????? / ?????? ??????
        List<Product> productList = getProductList(subcatMap);
        if (productList.size() < 10) {
            int rest = 10 - productList.size();
            productList = getRestItem(productList,rest);
        }


        return productList;
    }

    private List<Product> getRestItem(List<Product> productList, int rest) {
        System.out.println("rest item");
        Pageable pageable = PageRequest.of(0, rest, Sort.by("score").descending());
        List<Product> restList = productRepository.findAll(pageable).toList();
        int cnt =0;
        for (Product newProduct : restList) {
            for (Product product : productList) {
                if (newProduct.getWebUrl() == product.getWebUrl()) {
                    cnt +=1;
                    break;
                }

            }
            productList.add(newProduct);
        }

        getRestDuplicate(productList, cnt);
        return productList;

    }

    private void getRestDuplicate(List<Product> productList, int cnt) {
        if (cnt != 0) {
            List<Product> restList2;
            Pageable pageable2 = PageRequest.of(0, cnt, Sort.by("score").descending());
            restList2 = productRepository.findAll(pageable2).toList();
            for (Product product : restList2) {
                productList.add(product);
            }
        }
    }


    public List<SelectItemLog> getSelectItemTop10RecentlyByMemberidx(Long memberidx) {
        Sort sort = sortByRecent();
        List<SelectItemLog> logList = selectItemLogRepository.findTop10ByMemberidx(memberidx, sort);
        if (logList == null) {
            return null;
        }
        return logList;
    }

    private List<Product> getRecommendWithoutLogs() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("score").descending());
        return productRepository.findAll(pageable).toList();
    }

    private HashMap<String, Integer> getSubcat(List<SelectItemLog> logList) {
        HashMap<String, Integer> map = new HashMap<>();
        for (SelectItemLog key : logList) {

            String subcat = key.getSubcat();
            ;

            if (map.containsKey(subcat)) {
                map.replace(subcat, map.get(subcat) + 1);
            } else {
                map.put(key.getSubcat(), 1);
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
                countList.set(cnt % countList.size(), countList.get(cnt % countList.size()) + 1);
            }
            cnt += 1;
        }

        return weightList;
    }

    private Sort sortByRecent() {
        return Sort.by(Sort.Direction.DESC, "updateat");
    }

}
