package com.kcs.search.repository;

import com.kcs.search.document.SelectItemLog;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.List;

@EnableElasticsearchRepositories
public interface SelectItemLogRepository extends
        ElasticsearchRepository<SelectItemLog, String>,
        BaseElasticSearchRepository<SelectItemLog> {
    SelectItemLog findByMemberidxAndWebUrl(Long memberidx, String itemurl);

    List<SelectItemLog> findTop10ByMemberidx(Long memberidx, Sort sort);
}
