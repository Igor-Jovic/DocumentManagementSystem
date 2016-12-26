package rs.ac.bg.fon.silab.dms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import rs.ac.bg.fon.silab.dms.model.Document;
import rs.ac.bg.fon.silab.dms.repository.DocumentRepository;
import rs.ac.bg.fon.silab.dms.service.DocumentService;

import java.util.List;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class DocumentManagementSystemApp {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(DocumentManagementSystemApp.class, args);

        DocumentService documentService = (DocumentService) applicationContext.getBean("documentService");
        documentService.getAllDocuments();
    }
}
