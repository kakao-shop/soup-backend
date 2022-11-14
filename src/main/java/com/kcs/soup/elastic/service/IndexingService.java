package com.kcs.soup.elastic.service;

import com.kcs.soup.common.config.ElasitcConfig;
import com.kcs.soup.elastic.repository.ProductRepository;
import com.kcs.soup.elastic.utils.IndexUtil;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;


@Service
@RequiredArgsConstructor
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
            CreateIndexResponse createIndexResponse = elasitcConfig.client().indices().create(request, RequestOptions.DEFAULT);
            System.out.println("응답 : " + createIndexResponse.index());
        } catch (Exception e) {
            System.out.println(e);
        }

        IndexCoordinates indexNameWrapper = IndexUtil.createIndexNameWithPostFixWrapper(INDEX_PREFIX_NAME);
        IndexCoordinates aliasNameWrapper = IndexUtil.createIndexNameWrapper(ALIAS_NAME);
        System.out.println("aliasNameWrapper : " + aliasNameWrapper);
        System.out.println("indexNameWrapper : " + indexNameWrapper.getIndexName());
        baseElasticSearchRepo.setAlias(indexNameWrapper, aliasNameWrapper);
        System.out.println("=============================end");

    }

}
