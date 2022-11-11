package com.example.soup.elastic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.List;

@EnableElasticsearchRepositories
public interface KeywordRepository extends
        ElasticsearchRepository<KeywordLog, String>,
        BaseElasticSearchRepository<KeywordLog>{
    Boolean existsByMemberidxAndKeyword(Long memberidx, String keyword);

    KeywordLog findByMemberidxAndKeyword(Long memberidx, String keyword);
    List<KeywordLog> findTop3ByMemberidx(Long memberid, Sort sort);

}
