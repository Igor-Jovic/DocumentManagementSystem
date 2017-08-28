/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.core.repository.es;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import rs.ac.bg.fon.silab.dms.core.model.UserES;

/**
 *
 * @author stefan
 */
public interface UserESRepository extends ElasticsearchRepository<UserES, Long> {

    Page<UserES> findByUsernameLike(String username, Pageable pageable);

    Page<UserES> findByCompanyNameLike(String companyName, Pageable pageable); 
   
}
