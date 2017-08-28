/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.core.repository.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import rs.ac.bg.fon.silab.dms.core.model.DocumentES;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;

/**
 *
 * @author stefan
 */
public interface DocumentESRepository extends ElasticsearchRepository<DocumentES, Long> {

    @Query("{\n"
            + "   \"bool\":{\n"
            + "      \"must\":{\n"
            + "         \"term\":{\n"
            + "            \"companyId\":\"?0\"\n"
            + "         }\n"
            + "      },\n"
            + "      \"must\":[\n"
            + "         {\n"
            + "            \"multi_match\":{\n"
            + "               \"query\":\"?1\",\n"
            + "               \"fields\":[\n"
            + "                  \"name\",\n"
            + "                  \"description\"\n"
            + "               ]\n"
            + "            }\n"
            + "         },\n"
            + "         {\n"
            + "            \"nested\":{\n"
            + "               \"path\":\"documentDescriptors\",\n"
            + "               \"query\":{\n"
            + "                  \"bool\":{\n"
            + "                     \"should\":[\n"
            + "                        {\n"
            + "                           \"term\":{\n"
            + "                              \"documentDescriptors.name\":\"?1\"\n"
            + "                           }\n"
            + "                        },\n"
            + "                        {\n"
            + "                           \"term\":{\n"
            + "                              \"documentDescriptors.value\":\"?1\"\n"
            + "                           }\n"
            + "                        }\n"
            + "                     ]\n"
            + "                  }\n"
            + "               }\n"
            + "            }\n"
            + "         }\n"
            + "      ]\n"
            + "   }\n"
            + "}")
    Page<DocumentES> findBy(Long companyId, String searchExpression, Pageable pageable);

}
