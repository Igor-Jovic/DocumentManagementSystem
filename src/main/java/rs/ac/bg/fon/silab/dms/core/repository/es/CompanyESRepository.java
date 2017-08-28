/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.core.repository.es;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import rs.ac.bg.fon.silab.dms.core.model.CompanyES;

/**
 *
 * @author stefan
 */
public interface CompanyESRepository extends ElasticsearchRepository<CompanyES, Long> {

    Page<CompanyES> findByNameLike(String companyName, Pageable pageable); 
   
}
