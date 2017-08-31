/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.core.loader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.silab.dms.core.service.ElasticsearchClient;
import rs.ac.bg.fon.silab.dms.core.model.CompanyES;
import rs.ac.bg.fon.silab.dms.core.model.DocumentES;
import rs.ac.bg.fon.silab.dms.core.model.UserES;
import rs.ac.bg.fon.silab.dms.core.repository.CompanyRepository;
import rs.ac.bg.fon.silab.dms.core.repository.UserRepository;
import rs.ac.bg.fon.silab.dms.core.service.DocumentService;

/**
 *
 * @author stefan
 */
@Component
public class ElasticsearchLoader {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    DocumentService documentService;

    @Autowired
    Environment environment;

    @Autowired
    ElasticsearchClient elasticsearchClient;

    @PostConstruct
    @Transactional
    public void load() throws IOException {
        if (!Arrays.asList(environment.getActiveProfiles()).contains("test")) {
            elasticsearchClient.createIndex("dms");
            XContentBuilder userMapping = XContentFactory.jsonBuilder().prettyPrint()
                    .startObject()
                        .startObject("user")
                            .startObject("properties")
                                .startObject("id")
                                    .field("type", "integer")
                                .endObject()
                                .startObject("username")
                                    .field("type", "string")
                                .endObject()
                                .startObject("companyName")
                                    .field("type", "string")
                                .endObject()
                                .startObject("role")
                                    .field("type", "string")
                                .endObject()
                            .endObject()
                        .endObject()
                    .endObject();
            elasticsearchClient.createMapping(userMapping, "dms", "user");
            List<UserES> users = userRepository.findAll().stream().map(e -> new UserES(e)).collect(Collectors.toList());
            if (!users.isEmpty()) {
                elasticsearchClient.saveUsers(users);
            }

            XContentBuilder companyMapping = XContentFactory.jsonBuilder().prettyPrint()
                    .startObject()
                        .startObject("company")
                            .startObject("properties")
                                .startObject("id")
                                    .field("type", "integer")
                                .endObject()
                                .startObject("name")
                                    .field("type", "string")
                                .endObject()
                                .startObject("description")
                                    .field("type", "string")
                                .endObject()
                            .endObject()
                        .endObject()
                    .endObject();
            elasticsearchClient.createMapping(companyMapping, "dms", "company");
            List<CompanyES> companies = companyRepository.findAll().stream().map(e -> new CompanyES(e)).collect(Collectors.toList());
            if (!companies.isEmpty()) {
                elasticsearchClient.saveCompanies(companies);
            }
            XContentBuilder documentMapping = XContentFactory.jsonBuilder().prettyPrint()
                    .startObject()
                        .startObject("document")
                            .startObject("properties")
                                .startObject("id")
                                    .field("type", "integer")
                                .endObject()
                                .startObject("name")
                                    .field("type", "string")
                                .endObject()
                                .startObject("documentTypeId")
                                    .field("type", "integer")
                                .endObject()
                                .startObject("downloadLink")
                                    .field("type", "string")
                                .endObject()
                                .startObject("companyId")
                                    .field("type", "integer")
                                .endObject()
                                    .startObject("documentDescriptors")
                                        .field("type", "nested")
                                        .startObject("properties")
                                            .startObject("descriptor")
                                                .field("type", "string")
                                            .endObject()
                                            .startObject("descriptor")
                                                .field("type", "string")
                                            .endObject()
                                        .endObject()
                                    .endObject()
                            .endObject()
                        .endObject()
                    .endObject();
            elasticsearchClient.createMapping(documentMapping, "dms", "document");
            List<DocumentES> documents = documentService.getAll();
            if (!documents.isEmpty()) {
                elasticsearchClient.saveDocuments(documents);
            }
        }
    }

}
