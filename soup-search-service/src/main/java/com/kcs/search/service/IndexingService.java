package com.kcs.search.service;

import com.kcs.search.config.ElasitcConfig;
import com.kcs.search.repository.elasticsearch.ProductRepository;
import com.kcs.search.utils.IndexUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.elasticsearch.xcontent.XContentFactory.jsonBuilder;


@Service
@RequiredArgsConstructor
@Slf4j
public class IndexingService {
    private final ProductRepository baseElasticSearchRepo;
    private final ElasitcConfig elasitcConfig;
    private static final String INDEX_PREFIX_NAME = "product";
    private static final String ALIAS_NAME = "product";


    @Scheduled(cron = "0 */30 * * * *")
    public void indexingUserDate() throws InterruptedException {
        System.out.println("=============================start");

        Thread.sleep(2000);
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

        String formatedNow = formatter.format(now);
        System.out.println("formatedNow : " + formatedNow);
        String indexName = "product-" + formatedNow;
        CreateIndexRequest request = new CreateIndexRequest(indexName);

        try {
            request = indexSetting(request);
        } catch (IOException e) {
            log.error("fail index settings");
        }


        System.out.println("?");
        try {
            CreateIndexResponse createIndexResponse = elasitcConfig.client().indices().create(request, RequestOptions.DEFAULT);
            log.info("create index : " + createIndexResponse.index());
        } catch (Exception e) {
            System.out.println(e);
        }

        IndexCoordinates indexNameWrapper = IndexUtil.createIndexNameWithPostFixWrapper(INDEX_PREFIX_NAME);
        IndexCoordinates aliasNameWrapper = IndexUtil.createIndexNameWrapper(ALIAS_NAME);
        baseElasticSearchRepo.setAlias(indexNameWrapper, aliasNameWrapper);

    }

    private CreateIndexRequest indexSetting(CreateIndexRequest request) throws IOException {
        return request.settings(Settings.builder()
                .put("index.max_inner_result_window", 1000)
                .put("index.write.wait_for_active_shards", 1)
                .put("index.query.default_field", "prdName")
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 0)
                .loadFromSource(Strings.toString(jsonBuilder()
                        .startObject()
                        .field("merge.policy.max_merged_segment", "2g")
                        .startObject("analysis")
                        .startObject("analyzer")
                        .startObject("default")
                        .field("type", "custom")
                        .field("tokenizer", "nori_tokenizer")
                        .field("filter", "synonym")
                        .endObject()
                        .endObject()
                        .startObject("filter")
                        .startObject("synonym")
                        .field("type", "synonym")
                        .field("synonyms_path", "analysis/synonyms.txt")
                        .endObject()
                        .endObject()
                        .endObject()
                        .endObject()), XContentType.JSON)
        );
    }
}
