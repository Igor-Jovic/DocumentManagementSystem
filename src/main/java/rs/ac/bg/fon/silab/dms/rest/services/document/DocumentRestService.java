/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.core.model.Descriptor;
import rs.ac.bg.fon.silab.dms.core.model.Document;
import rs.ac.bg.fon.silab.dms.core.model.DocumentDescriptorAssociation;
import rs.ac.bg.fon.silab.dms.core.model.DocumentType;
import rs.ac.bg.fon.silab.dms.core.service.DocumentService;
import rs.ac.bg.fon.silab.dms.core.service.DocumentTypeService;
import static rs.ac.bg.fon.silab.dms.rest.model.ApiResponse.createSuccessResponse;
import rs.ac.bg.fon.silab.dms.rest.services.document.dto.DocumentDescriptorRequest;
import rs.ac.bg.fon.silab.dms.rest.services.document.dto.DocumentRequest;
import rs.ac.bg.fon.silab.dms.rest.services.document.dto.DocumentResponse;

/**
 *
 * @author stefan
 */
@RestController
@RequestMapping("/documents")
public class DocumentRestService {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentTypeService documentTypeService;

    @PostMapping
    public ResponseEntity create(@RequestBody DocumentRequest documentRequest) throws BadRequestException {
        Document document = documentService.createDocument(createDocumentFromRequest(documentRequest));
        List<DocumentDescriptorAssociation> associations = createDescriptorsAssotiations(document, documentRequest);
        documentService.addDescriptors(associations);
        document.setDocumentDescriptorAssociationList(associations);
        
        DocumentResponse documentResponse = new DocumentResponse(document);
        return ResponseEntity.ok(createSuccessResponse(documentResponse));
    }

    @GetMapping
    public ResponseEntity getAll() {
        List<Document> allDocuments = documentService.getAllDocument();

        List<DocumentResponse> documentResponses = DocumentResponse.getDocumentResponseList(allDocuments);
        return ResponseEntity.ok(createSuccessResponse(documentResponses));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getOne(@PathVariable("id") Long id) throws BadRequestException {
        Document document = documentService.getDocument(id);

        DocumentResponse documentResponse = new DocumentResponse(document);
        return ResponseEntity.ok(createSuccessResponse(documentResponse));
    }

    private Document createDocumentFromRequest(DocumentRequest documentRequest) throws BadRequestException {
        Document document = new Document();
        DocumentType documentType = documentTypeService.getDocumentType(documentRequest.getDocumentTypeId());
        document.setDocumentType(documentType);

        return document;
    }

    public List<DocumentDescriptorAssociation> createDescriptorsAssotiations(Document document, DocumentRequest documentRequest) throws BadRequestException {
        boolean validDescriptors = document.getDocumentType().getDescriptors().stream()
                .map(Descriptor::getId)
                .collect(Collectors.toList())
                .containsAll(documentRequest.getDescriptorRequests().stream()
                        .map(DocumentDescriptorRequest::getDescriptorId)
                        .collect(Collectors.toList()));
        if (!validDescriptors) {
            throw new BadRequestException("Invalid descriptors.");
        }
        List<DocumentDescriptorAssociation> associations = documentRequest.getDescriptorRequests().stream()
                .map(e -> new DocumentDescriptorAssociation(
                        document, document.getDocumentType().getDescriptors().stream()
                                .filter(d -> d.getId().equals(e.getDescriptorId()))
                                .findFirst().get(), e.getValue())).collect(Collectors.toList());
        return associations;
        
    }

}
