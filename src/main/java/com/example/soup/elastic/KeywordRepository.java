package com.example.soup.elastic;

import com.example.soup.domain.entity.elasticsearch.KeywordLog;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface KeywordRepository extends
        ElasticsearchRepository<KeywordLog, String>,
        BaseElasticSearchRepository<KeywordLog> {
    Boolean existsByMemberidxAndKeyword(Long memberidx, String keyword);

    KeywordLog findByMemberidxAndKeyword(Long memberidx, String keyword);

    List<KeywordLog> findTop3ByMemberidx(Long memberid, Sort sort);

}
