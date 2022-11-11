package com.example.soup.elastic;


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
    private final ProductRepository productRepository;
    private final KeywordRepository keywordRepository;
    public Page<Product> getProductPage(String prdname, Pageable pageable,Long memberidx) {

        KeywordLog keywordObject = keywordRepository.findByMemberidxAndKeyword(memberidx, prdname);

        saveKeywordLog(prdname, memberidx, keywordObject);
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

        List<Product> productList = getProductList2(data);

//        List<Product> productList = getProductList2(subCatList, weigthList);
        // subcat에서 상품 정보 가져오기


        System.out.println(productList);

        return productList;
    }

    private List<Product> getProductList2(HashMap<String, Integer> data) {
        List<Product> productList = new ArrayList<>();
        data.forEach((k, v) ->{
                Pageable pageable = PageRequest.of(1, v, Sort.by(Sort.Direction.DESC, "purchase"));
                Page<Product> products = productRepository.findBySubcat(k, pageable);
                for (Product product : products) {
                    productList.add(product);

            }
        });

        return productList;
    }


    private List<Product> getProductList(List<String> subCatList, List<Integer> weigthList) {
        List<Product> productList = new ArrayList<>();

        for (int i = 0; i < subCatList.size(); i++) {
            Pageable pageable = PageRequest.of(1, weigthList.get(i), Sort.by(Sort.Direction.DESC, "purchase"));
            Page<Product> products = productRepository.findBySubcat(subCatList.get(i), pageable);
            for (Product product : products) {
                productList.add(product);
            }
        }

        return productList;
    }

    private List<String> getSubcat(String[] keyList) {
        List<String> subCatList = new ArrayList<>();
        for (String key: keyList) {
            Pageable pageable = PageRequest.of(1, 1, Sort.by(Sort.Direction.DESC, "purchase"));
            Product product = productRepository.findByPrdName(key, pageable).get().collect(Collectors.toList()).get(0);
            subCatList.add(product.getSubcat());
        }
        return subCatList;
    }

//    private List<Product> getRecommendItem(String[] keyList, List<Integer> weigthList) {
//
//    }

    private List<Integer> getWeight(String[] keyList, int[] countList) {
        Integer tmp = Arrays.stream(countList).sum();
        List<Integer> weightList;
        Integer cnt = 0;
        while (true) {
            Integer re = 0;
            weightList = new ArrayList<>();
            for (int i=0; i < countList.length; i++) {
                re += Integer.valueOf(countList[i] / tmp);
                weightList.add(Integer.valueOf((countList[i] / tmp)));
            }
            if (re == 10) {
                break;
            } else {
                countList[cnt % countList.length] += 1;
            }
            cnt += 1;
        }
        System.out.println(weightList);


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





}
