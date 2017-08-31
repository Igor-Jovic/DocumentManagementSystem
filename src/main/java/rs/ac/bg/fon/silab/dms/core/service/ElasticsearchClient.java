/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.core.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.dms.core.model.CompanyES;
import rs.ac.bg.fon.silab.dms.core.model.DocumentES;
import rs.ac.bg.fon.silab.dms.core.model.UserES;

/**
 *
 * @author stefan
 */
@Service
public class ElasticsearchClient {

    private TransportClient client;

    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

    @PostConstruct
    private Client createClient() {
        try {
            Settings settings = Settings.settingsBuilder()
                    .put("cluster.name", "elasticsearch")
                    .build();
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(
                                    InetAddress.getByName("localhost"), 9300));
            return client;
        } catch (UnknownHostException ex) {
            Logger.getLogger(ElasticsearchClient.class.getName()).severe(ex.getMessage());
            return null;
        }
    }

    public void createIndex(String indexName) {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
        client.admin().indices().delete(deleteIndexRequest).actionGet();
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        client.admin().indices().create(createIndexRequest).actionGet();
    }

    public void createMapping(XContentBuilder mapping, String index, String type) {
        client.admin().indices()
                .preparePutMapping(index)
                .setType(type)
                .setSource(mapping)
                .execute().actionGet();
    }

    public List<CompanyES> findCompaniesByNameLike(String companyName, Pageable pageable) {
        SearchResponse response = client.prepareSearch("dms")
                .setTypes("company")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchPhrasePrefixQuery("name", companyName))
                .setFrom(pageable.getPageSize() * pageable.getPageNumber()).setSize(pageable.getPageSize())
                .execute().actionGet();
        return populateSearchList(response, CompanyES.class);
    }

    public List<DocumentES> findDocumentsBy(Long companyId, String searchExpression, Pageable pageable) {
        SearchRequestBuilder builder = client.prepareSearch("dms")
                .setTypes("document")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

        BoolQueryBuilder query = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("companyId", companyId))
                .must(QueryBuilders.boolQuery()
                        .should(QueryBuilders.multiMatchQuery(searchExpression, "description", "name"))
                        .should(QueryBuilders.nestedQuery("documentDescriptors", QueryBuilders.boolQuery()
                                        .should(QueryBuilders.termQuery("documentDescriptors.name", searchExpression))
                                        .should(QueryBuilders.termQuery("documentDescriptors.value", searchExpression)))));
        builder = builder.setQuery(query);

        SearchResponse response = builder.setFrom(pageable.getPageSize() * pageable.getPageNumber()).setSize(pageable.getPageSize())
                .execute().actionGet();
        return populateSearchList(response, DocumentES.class);
    }

    public List<UserES> findUsersByNameLike(String username, Pageable pageable) {
        SearchResponse response = client.prepareSearch("dms")
                .setTypes("user")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchPhrasePrefixQuery("username", username))
                .setFrom(pageable.getPageSize() * pageable.getPageNumber()).setSize(pageable.getPageSize())
                .execute().actionGet();
        return populateSearchList(response, UserES.class);
    }

    public List<CompanyES> findComapniesByNameOrDescription(String searchExpression, Pageable pageable) {
        SearchResponse response = client.prepareSearch("dms")
                .setTypes("company")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(new BoolQueryBuilder().should(QueryBuilders.matchPhrasePrefixQuery("name", searchExpression))
                        .should(QueryBuilders.matchQuery("description", searchExpression).fuzziness(Fuzziness.TWO)))
                .setFrom(pageable.getPageSize() * pageable.getPageNumber()).setSize(pageable.getPageSize())
                .execute().actionGet();

        return populateSearchList(response, CompanyES.class);
    }

    public <T> List<T> populateSearchList(SearchResponse response, Class<T> clazz) {
        SearchHit[] hits = response.getHits().getHits();
        List<T> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            T element = mapper.convertValue(hit.getSource(), clazz);
            list.add(element);
        }
        return list;
    }

    public void save(UserES user) {
        try {
            IndexRequest indexRequest = new IndexRequest("dms", "user")
                    .id(String.valueOf(user.getId())).source(mapper.writeValueAsString(user));
            client.index(indexRequest).actionGet();
        } catch (IOException ex) {
            Logger.getLogger(ElasticsearchClient.class.getName()).severe(ex.getMessage());
        }
    }

    public void saveUsers(List<UserES> users) {
        for (UserES user : users) {
            save(user);
        }
    }

    public void save(CompanyES company) {
        try {
            IndexRequest indexRequest = new IndexRequest("dms", "company")
                    .id(String.valueOf(company.getId())).source(mapper.writeValueAsString(company));
            client.index(indexRequest).actionGet();
        } catch (IOException ex) {
            Logger.getLogger(ElasticsearchClient.class.getName()).severe(ex.getMessage());
        }
    }

    public void saveCompanies(List<CompanyES> companies) {
        for (CompanyES company : companies) {
            save(company);
        }
    }

    public void save(DocumentES document) {
        try {
            IndexRequest indexRequest = new IndexRequest("dms", "document")
                    .id(String.valueOf(document.getId())).source(mapper.writeValueAsString(document));
            client.index(indexRequest).actionGet();
        } catch (IOException ex) {
            Logger.getLogger(ElasticsearchClient.class.getName()).severe(ex.getMessage());
        }
    }

    public void saveDocuments(List<DocumentES> documents) {
        for (DocumentES document : documents) {
            save(document);
        }
    }

}
