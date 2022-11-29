package com.kcs.search.repository.elasticsearch;

import com.kcs.search.document.SelectItemLog;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SelectItemLogRepository extends
        ElasticsearchRepository<SelectItemLog, String>,
        BaseElasticSearchRepository<SelectItemLog> {
    SelectItemLog findByMemberidxAndWebUrl(Long memberidx, String itemurl);

    List<SelectItemLog> findTop10ByMemberidx(Long memberidx, Sort sort);
}
