/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.dms.core.exception.DMSErrorException;
import rs.ac.bg.fon.silab.dms.core.model.*;
import rs.ac.bg.fon.silab.dms.core.service.ActivityService;
import rs.ac.bg.fon.silab.dms.core.service.DocumentService;
import rs.ac.bg.fon.silab.dms.core.service.DocumentTypeService;
import rs.ac.bg.fon.silab.dms.core.service.UserService;
import rs.ac.bg.fon.silab.dms.rest.services.document.dto.DocumentDescriptorRequest;
import rs.ac.bg.fon.silab.dms.rest.services.document.dto.DocumentRequest;
import rs.ac.bg.fon.silab.dms.rest.services.document.dto.DocumentResponse;
import rs.ac.bg.fon.silab.dms.security.TokenAuthenticationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static rs.ac.bg.fon.silab.dms.rest.model.ApiResponse.createSuccessResponse;
import static rs.ac.bg.fon.silab.dms.rest.services.document.dto.DocumentResponse.getDocumentResponseList;

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
    private TokenAuthenticationService tokenAuthenticationService;

    @PostMapping
    public ResponseEntity create(@RequestHeader("X-Authorization") String token, @RequestBody DocumentRequest documentRequest) throws DMSErrorException {
        User authenticatedUser = tokenAuthenticationService.getAuthenticatedUser(token);

        Document document = documentService.createDocument(createDocumentFromRequest(documentRequest, authenticatedUser.getCompany()));
        List<DocumentDescriptorAssociation> associations = createDescriptorsAssotiations(document, documentRequest);
        documentService.addDescriptors(associations);
        document.setDocumentDescriptorAssociationList(associations);

        DocumentResponse documentResponse = new DocumentResponse(document);
        return ResponseEntity.ok(createSuccessResponse(documentResponse));
    }

    @GetMapping
    public ResponseEntity getAll(@RequestHeader("X-Authorization") String token) {
        User authenticatedUser = tokenAuthenticationService.getAuthenticatedUser(token);
        List<Document> documents = documentService.getAllDocumentsByCompanyId(authenticatedUser.getCompany().getId());
        List<DocumentResponse> documentResponses = getDocumentResponseList(documents);
        return ResponseEntity.ok(createSuccessResponse(documentResponses));
    }

    @GetMapping(value = "/activities/{activityId}")
    public ResponseEntity getAllForActivity(@PathVariable("activityId") Long activityId) {

        List<Document> allInputDocumentsForActivity = documentService.getAllInputDocumentsForActivity(activityId);
        List<Document> allOutputDocumentsForActivity = documentService.getAllOutputDocumentsForActivity(activityId);


        Map<String, List<DocumentResponse>> response = new HashMap<>();
        response.put("inputs", getDocumentResponseList(allInputDocumentsForActivity));
        response.put("outputs", getDocumentResponseList(allOutputDocumentsForActivity));
        return ResponseEntity.ok(createSuccessResponse(response));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getOne(@RequestHeader("X-Authorization") String token, @PathVariable("id") Long id) throws DMSErrorException {
        User authenticatedUser = tokenAuthenticationService.getAuthenticatedUser(token);
        Document document = documentService.getDocument(id);

        if (!document.getDocumentType().getCompany().equals(authenticatedUser.getCompany())) {
            throw new DMSErrorException("Your company does not have specified document.");
        }

        DocumentResponse documentResponse = new DocumentResponse(document);
        return ResponseEntity.ok(createSuccessResponse(documentResponse));
    }

    private Document createDocumentFromRequest(DocumentRequest documentRequest, Company company) throws DMSErrorException {
        Document document = new Document();
        DocumentType documentType = documentTypeService.getDocumentType(documentRequest.getId());

        if (!company.equals(documentType.getCompany())) {
            throw new DMSErrorException("Your company does not have specified document type.");
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

    private List<DocumentDescriptorAssociation> createDescriptorsAssotiations(Document document, DocumentRequest documentRequest) throws DMSErrorException {
        boolean validDescriptors = document.getDocumentType().getDescriptors().stream()
                .map(Descriptor::getId)
                .collect(Collectors.toList())
                .containsAll(documentRequest.getDescriptors().stream()
                        .map(DocumentDescriptorRequest::getId)
                        .collect(Collectors.toList()));
        if (!validDescriptors) {
            throw new DMSErrorException("Invalid descriptors.");
        }
        return documentRequest.getDescriptors().stream()
                .map(e -> new DocumentDescriptorAssociation(
                        document, document.getDocumentType().getDescriptors().stream()
                        .filter(d -> d.getId().equals(e.getId()))
                        .findFirst().orElse(null), e.getValue())).collect(Collectors.toList());

    }

}
