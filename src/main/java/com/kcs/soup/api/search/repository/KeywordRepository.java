package com.kcs.soup.api.search.repository;

import com.kcs.soup.api.search.document.KeywordLog;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.List;

@EnableElasticsearchRepositories
public interface KeywordRepository extends
        ElasticsearchRepository<KeywordLog, String>,
        BaseElasticSearchRepository<KeywordLog> {

    List<KeywordLog> findTop10ByMemberidx(Long memberidx, Sort sort);

}
