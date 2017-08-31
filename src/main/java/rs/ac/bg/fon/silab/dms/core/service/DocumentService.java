/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.dms.core.exception.DMSErrorException;
import rs.ac.bg.fon.silab.dms.core.model.Document;
import rs.ac.bg.fon.silab.dms.core.model.DocumentDescriptorAssociation;
import rs.ac.bg.fon.silab.dms.core.repository.DocumentDescriptorAssociationRepository;
import rs.ac.bg.fon.silab.dms.core.repository.DocumentRepository;
import java.util.List;
import java.util.stream.Collectors;
import rs.ac.bg.fon.silab.dms.core.model.DocumentES;
import org.springframework.data.domain.Pageable;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentDescriptorAssociationRepository descriptorAssociationRepository;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    DocumentService(DocumentRepository documentRepository, DocumentDescriptorAssociationRepository descriptorAssociationRepository,
            ElasticsearchClient elasticsearchClient) {
        this.documentRepository = documentRepository;
        this.descriptorAssociationRepository = descriptorAssociationRepository;
        this.elasticsearchClient = elasticsearchClient;
    }

    public Document createDocument(Document document) {
        document = documentRepository.saveAndFlush(document);
        elasticsearchClient.save(new DocumentES(document));
        return document;
    }

    public void addDescriptors(List<DocumentDescriptorAssociation> associations) {
        for (DocumentDescriptorAssociation descriptor : associations) {
            descriptorAssociationRepository.save(descriptor);
        }
        descriptorAssociationRepository.flush();
    }

    public List<Document> getAllDocumentsByCompanyId(Long companyId) {
        return documentRepository.getAllDocumentByCompanyId(companyId);
    }

    public List<Document> getAllInputDocumentsForActivity(Long activityId) {
        return documentRepository.getAllDocumentByInputForActivityId(activityId);
    }

    public List<Document> getAllOutputDocumentsForActivity(Long activityId) {
        return documentRepository.getAllDocumentByOutputForActivityId(activityId);
    }

    public Document getDocument(Long id) throws DMSErrorException {
        Document document = documentRepository.findOne(id);
        if (document == null) {
            throw new DMSErrorException("Document does not exists.");
        }
        return document;
    }

    public List<DocumentES> getAll() {
        return documentRepository.findAll().stream().map(e -> new DocumentES(e)).collect(Collectors.toList());
    }

    public List<DocumentES> getAllFor(Long companyId, String searchExpression, Pageable page) {
        return elasticsearchClient.findDocumentsBy(companyId, searchExpression, page);
    }

}
