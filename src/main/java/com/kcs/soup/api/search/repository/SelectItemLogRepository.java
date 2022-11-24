package com.kcs.soup.api.search.repository;

import com.kcs.soup.api.search.document.SelectItemLog;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.List;

@EnableElasticsearchRepositories
public interface SelectItemLogRepository extends
        ElasticsearchRepository<SelectItemLog, String>,
        BaseElasticSearchRepository<SelectItemLog> {
    SelectItemLog findByMemberidxAndItemurl(Long memberidx, String itemurl);

    List<SelectItemLog> findTop10ByMemberidx(Long memberidx, Sort sort);
}
