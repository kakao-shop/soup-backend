package com.kcs.soup.elastic.repository;

import com.kcs.soup.elastic.document.KeywordLog;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.List;

@EnableElasticsearchRepositories
public interface KeywordRepository extends
        ElasticsearchRepository<KeywordLog, String>,
        BaseElasticSearchRepository<KeywordLog> {
    Boolean existsByMemberidxAndKeyword(Long memberidx, String keyword);

    KeywordLog findByMemberidxAndKeyword(Long memberidx, String keyword);

    boolean existsKeywordLogByMemberidx(Long memeberid);

    List<KeywordLog> findTop10ByMemberidx(Long memberidx, Sort sort);
}
