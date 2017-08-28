package rs.ac.bg.fon.silab.dms;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
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

            @Autowired
            private Environment environment;

            @Override
            @Transactional
            public void run(String... strings) throws Exception {
                if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
                    User u = new User();
                    u.setUsername("admin");
                    u.setPassword("admin");
                    u.setCompany(new Company("adminCompany"));
                    u.setRole(Role.ADMIN);
                    User admin = userService.createUserWithNewCompany(u);
                    User u2 = new User();
                    u2.setUsername("notAdmin");
                    u2.setPassword("notAdmin");
                    u2.setRole(Role.USER);
                    u2.setCompany(admin.getCompany());
                    userService.createUser(u2);
                }
            }
        };
    }
}
