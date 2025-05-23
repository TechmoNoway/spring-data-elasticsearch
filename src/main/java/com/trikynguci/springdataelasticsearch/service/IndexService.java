package com.trikynguci.springdataelasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.IndexSettings;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trikynguci.springdataelasticsearch.helper.Indices;
import com.trikynguci.springdataelasticsearch.helper.Util;
import jakarta.annotation.PostConstruct;
import org.elasticsearch.client.RequestOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IndexService {
    private static final Logger LOG = LoggerFactory.getLogger(IndexService.class);
    private final List<String> INDICES_TO_CREATE = List.of(Indices.VEHICLE_INDEX);
    private final ElasticsearchClient client;

    @Autowired
    public IndexService(ElasticsearchClient client) {
        this.client = client;
    }

    @PostConstruct
    public void tryToCreateIndices(){
        final String settings = Util.loadAsString("static/es-settings.json");

        for (final String indexName : INDICES_TO_CREATE) {
            try {
                boolean indexExists = client.indices()
                        .exists(b -> b.index(indexName))
                        .value();
                if (indexExists) {
                    continue;
                }

                final String mappings = Util.loadAsString("static/mappings/" + indexName + ".json");
                if (settings == null || mappings == null) {
                    LOG.error("Filed to create index with name '{}'", indexName);
                    continue;
                }

                ObjectMapper objectMapper = new ObjectMapper();
                IndexSettings settingsObj = objectMapper.readValue(settings, IndexSettings.class);
                TypeMapping mappingsObj = objectMapper.readValue(mappings, TypeMapping.class);

                final CreateIndexRequest createIndexRequest = new CreateIndexRequest.Builder()
                        .index(indexName)
                        .settings(settingsObj)
                        .mappings(mappingsObj)
                        .build();

                client.indices().create(createIndexRequest);
            } catch (final Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }



}
