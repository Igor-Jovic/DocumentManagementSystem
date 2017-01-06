package rs.ac.bg.fon.silab.dms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "rs.ac.bg.fon.silab.dms")
public class DocumentManagementSystemApp {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(DocumentManagementSystemApp.class, args);
    }
}
