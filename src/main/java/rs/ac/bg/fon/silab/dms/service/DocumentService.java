package rs.ac.bg.fon.silab.dms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.dms.model.Document;
import rs.ac.bg.fon.silab.dms.model.DocumentDescriptorAssociation;
import rs.ac.bg.fon.silab.dms.repository.DocumentRepository;

import java.util.List;

@Service("documentService")
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public DocumentRepository getDocumentRepository() {
        return documentRepository;
    }

    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<Document> getAllDocuments() {
        List<Document> documents = documentRepository.findAll();
        Document document = documents.get(0);
        System.out.println(document.getName());
        System.out.println(document.getDocumentDescriptorAssociations().size());
        DocumentDescriptorAssociation documentDescriptorAssociation = document.getDocumentDescriptorAssociations().get(0);
        System.out.println(documentDescriptorAssociation.getDescriptor().getName());

        return documents;
    }
}
