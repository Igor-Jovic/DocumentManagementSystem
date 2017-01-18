/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.core.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.core.model.Document;
import rs.ac.bg.fon.silab.dms.core.model.DocumentDescriptorAssociation;
import rs.ac.bg.fon.silab.dms.core.repository.DocumentDescriptorAssociationRepository;
import rs.ac.bg.fon.silab.dms.core.repository.DocumentRepository;

/**
 *
 * @author stefan
 */
@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentDescriptorAssociationRepository descriptorAssociationRepository;

    DocumentService(DocumentRepository documentRepository, DocumentDescriptorAssociationRepository descriptorAssociationRepository) {
        this.documentRepository = documentRepository;
        this.descriptorAssociationRepository = descriptorAssociationRepository;
    }

    public Document createDocument(Document document) {
        return documentRepository.saveAndFlush(document);
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

    public Document getDocument(Long id) throws BadRequestException {
        Document document = documentRepository.findOne(id);
        if (document == null) {
            throw new BadRequestException("Document does not exists.");
        }
        return document;
    }

}