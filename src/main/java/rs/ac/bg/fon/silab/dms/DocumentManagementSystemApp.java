package rs.ac.bg.fon.silab.dms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.silab.dms.core.model.Company;
import rs.ac.bg.fon.silab.dms.core.model.Role;
import rs.ac.bg.fon.silab.dms.core.model.User;
import rs.ac.bg.fon.silab.dms.core.repository.ProcessRepository;
import rs.ac.bg.fon.silab.dms.core.service.UserService;

@SpringBootApplication
public class DocumentManagementSystemApp {

    public static void main(String[] args) {
        SpringApplication.run(DocumentManagementSystemApp.class, args);
    }

    @Bean
    CommandLineRunner init() {
        return new CommandLineRunner() {
            @Autowired
            private UserService userService;

            @Autowired
            ProcessRepository processRepository;

            public void setUserService(UserService userService) {
                this.userService = userService;
            }


            @Override
            @Transactional
            public void run(String... strings) throws Exception {
//                User u = new User();
//                u.setUsername("admin");
//                u.setPassword("admin");
//                u.setCompany(new Company("adminCompany"));
//                u.setRole(Role.ADMIN);
//                userService.createUserWithNewCompany(u);
            }
        };
    }
}
