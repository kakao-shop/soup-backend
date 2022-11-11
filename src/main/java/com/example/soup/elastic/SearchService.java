package com.example.soup.elastic;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public List<KeywordLog> getRecommendItemByMemberid(Long memberidx) {
        Sort sort = sortByCount();
        List<KeywordLog> logList = keywordRepository.findTop3ByMemberidx(memberidx, sort);
        String[] keyList = new String[logList.size()];
        Integer[] countList = new Integer[logList.size()];

        for (int i = 0; i < logList.size(); i++) {
            keyList[i] = logList.get(i).getKeyword();
            countList[i] = logList.get(i).getCount();
        }
//        List<Integer> weigthList = getWeight(keyList, countList);

        return logList;
    }

//    private List<Integer> getWeight(String[] keyList, Integer[] countList) {
//        Integer tmp = Arrays.stream(countList).s
//        List<Integer> weightList;
//        Integer cnt = 0;
//        while (true) {
//            Integer re = 0;
//            weightList = new ArrayList<>();
//            for (int i=0; i < countList.size(); i++) {
//                re += Integer.valueOf(countList.get(i) / tmp);
//
//            }
//            if (re == 10) {
//                break;
//            } else {
//                countList
//            }
//
//        }
//
//
//        return weightList;
//    }

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
