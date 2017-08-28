/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.core.loader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.silab.dms.core.model.UserES;
import rs.ac.bg.fon.silab.dms.core.repository.UserRepository;
import rs.ac.bg.fon.silab.dms.core.repository.es.UserESRepository;

/**
 *
 * @author stefan
 */
@Component
public class ElasticsearchLoader {

    @Autowired
    ElasticsearchOperations operations;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserESRepository userESRepository;

    @Autowired
    Environment environment;

    @PostConstruct
    @Transactional
    public void load() {
        if (!Arrays.asList(environment.getActiveProfiles()).contains("test")) {
            operations.putMapping(UserES.class);
            List<UserES> users = userRepository.findAll().stream().map(e -> new UserES(e)).collect(Collectors.toList());
            userESRepository.save(users);
        }
    }

}
