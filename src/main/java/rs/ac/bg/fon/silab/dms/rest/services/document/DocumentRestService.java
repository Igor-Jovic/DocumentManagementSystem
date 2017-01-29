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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.core.model.Activity;
import rs.ac.bg.fon.silab.dms.core.model.Company;
import rs.ac.bg.fon.silab.dms.core.model.Descriptor;
import rs.ac.bg.fon.silab.dms.core.model.Document;
import rs.ac.bg.fon.silab.dms.core.model.DocumentDescriptorAssociation;
import rs.ac.bg.fon.silab.dms.core.model.DocumentType;
import rs.ac.bg.fon.silab.dms.core.model.User;
import rs.ac.bg.fon.silab.dms.core.service.ActivityService;
import rs.ac.bg.fon.silab.dms.core.service.DocumentService;
import rs.ac.bg.fon.silab.dms.core.service.DocumentTypeService;
import rs.ac.bg.fon.silab.dms.core.service.UserService;
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
    private ActivityService activityService;

    @Autowired
    private DocumentTypeService documentTypeService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity create(@RequestHeader("X-Authorization") String token, @RequestBody DocumentRequest documentRequest) throws BadRequestException {
        User authenticatedUser = userService.getAuthenticatedUser(token);
        
        Document document = documentService.createDocument(createDocumentFromRequest(documentRequest, authenticatedUser.getCompany()));
        List<DocumentDescriptorAssociation> associations = createDescriptorsAssotiations(document, documentRequest);
        documentService.addDescriptors(associations);
        document.setDocumentDescriptorAssociationList(associations);

        DocumentResponse documentResponse = new DocumentResponse(document);
        return ResponseEntity.ok(createSuccessResponse(documentResponse));
    }

    @GetMapping
    public ResponseEntity getAll(@RequestHeader("X-Authorization") String token) {
        User authenticatedUser = userService.getAuthenticatedUser(token);
        List<Document> documents = documentService.getAllDocumentsByCompanyId(authenticatedUser.getCompany().getId());
//        List<Document> documents = authenticatedUser.getCompany().getDocumentTypes().stream()
//                .flatMap(e -> e.getDocuments().stream())
//                .collect(Collectors.toList());

        List<DocumentResponse> documentResponses = DocumentResponse.getDocumentResponseList(documents);
        return ResponseEntity.ok(createSuccessResponse(documentResponses));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getOne(@RequestHeader("X-Authorization") String token, @PathVariable("id") Long id) throws BadRequestException {
        User authenticatedUser = userService.getAuthenticatedUser(token);
        Document document = documentService.getDocument(id);

        if (!document.getDocumentType().getCompany().equals(authenticatedUser.getCompany())) {
            throw new BadRequestException("Your company does not have specified document.");
        }

        DocumentResponse documentResponse = new DocumentResponse(document);
        return ResponseEntity.ok(createSuccessResponse(documentResponse));
    }

    private Document createDocumentFromRequest(DocumentRequest documentRequest, Company company) throws BadRequestException {
        Document document = new Document();
        DocumentType documentType = documentTypeService.getDocumentType(documentRequest.getId());
        
        if (!company.equals(documentType.getCompany())) {
            throw new BadRequestException("Your company does not have specified document type.");
        }
        
        document.setDocumentType(documentType);
        document.setCompany(company);
        Activity activity = activityService.getActivity(documentRequest.getActivityId());
        
        // check if user can use activity
        if (documentRequest.isInput()) {
            document.setInputForActivity(activity);
        } else {
            document.setOutputForActivity(activity);
        }

        return document;
    }

    public List<DocumentDescriptorAssociation> createDescriptorsAssotiations(Document document, DocumentRequest documentRequest) throws BadRequestException {
        boolean validDescriptors = document.getDocumentType().getDescriptors().stream()
                .map(Descriptor::getId)
                .collect(Collectors.toList())
                .containsAll(documentRequest.getDescriptors().stream()
                        .map(DocumentDescriptorRequest::getId)
                        .collect(Collectors.toList()));
        if (!validDescriptors) {
            throw new BadRequestException("Invalid descriptors.");
        }
        List<DocumentDescriptorAssociation> associations = documentRequest.getDescriptors().stream()
                .map(e -> new DocumentDescriptorAssociation(
                                document, document.getDocumentType().getDescriptors().stream()
                                .filter(d -> d.getId().equals(e.getId()))
                                .findFirst().get(), e.getValue())).collect(Collectors.toList());
        return associations;

    }

}
