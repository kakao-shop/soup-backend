package com.example.soup.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories
public interface KeywordRepository extends
        ElasticsearchRepository<KeywordLog, String>,
        BaseElasticSearchRepository<KeywordLog>{
    Boolean existsByMemberidxAndKeyword(Long memberidx, String keyword);

    KeywordLog findByMemberidxAndKeyword(Long memberidx, String keyword);
}
