package com.trikynguci.springdataelasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.DeleteIndexRequest;
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


@Service
public class IndexService {
    private static final Logger LOG = LoggerFactory.getLogger(IndexService.class);
    private static final List<String> INDICES = List.of(Indices.VEHICLE_INDEX);
    private final ElasticsearchClient client;

    @Autowired
    public IndexService(ElasticsearchClient client) {
        this.client = client;
    }

    @PostConstruct
    public void tryToCreateIndices(){
        recreateIndices(false);
    }

    public void recreateIndices(final boolean deleteExisting) {
        final String settings = Util.loadAsString("static/es-settings.json");

        if (settings == null) {
            LOG.error("Failed to load index settings");
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();

        for (final String indexName : INDICES) {
            try {
                final boolean indexExists = client.indices()
                        .exists(b -> b.index(indexName))
                        .value();
                if (indexExists) {
                    if (!deleteExisting) {
                        continue;
                    }
                    client.indices().delete(b -> b.index(indexName));
                }

                IndexSettings settingsObj = objectMapper.readValue(settings, IndexSettings.class);

                final String mappings = loadMappings(indexName);
                TypeMapping mappingsObj = null;
                if (mappings != null) {
                    mappingsObj = objectMapper.readValue(mappings, TypeMapping.class);
                }

                CreateIndexRequest.Builder builder = new CreateIndexRequest.Builder()
                        .index(indexName)
                        .settings(settingsObj);

                if (mappingsObj != null) {
                    builder.mappings(mappingsObj);
                }

                client.indices().create(builder.build());
            } catch (final Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    private String loadMappings(String indexName) {
        final String mappings = Util.loadAsString("static/mappings/" + indexName + ".json");
        if (mappings == null) {
            LOG.error("Failed to load mappings for index with name '{}'", indexName);
            return null;
        }

        return mappings;
    }


}
