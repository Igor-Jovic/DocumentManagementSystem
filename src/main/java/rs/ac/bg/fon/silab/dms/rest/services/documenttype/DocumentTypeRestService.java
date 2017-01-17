/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.documenttype;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.core.model.Company;
import rs.ac.bg.fon.silab.dms.core.model.Descriptor;
import rs.ac.bg.fon.silab.dms.core.model.DocumentType;
import rs.ac.bg.fon.silab.dms.core.model.User;
import rs.ac.bg.fon.silab.dms.core.service.DocumentTypeService;
import rs.ac.bg.fon.silab.dms.core.service.UserService;
import static rs.ac.bg.fon.silab.dms.rest.model.ApiResponse.createSuccessResponse;
import rs.ac.bg.fon.silab.dms.rest.services.documenttype.dto.DocumentTypeRequest;
import rs.ac.bg.fon.silab.dms.rest.services.documenttype.dto.DocumentTypeResponse;

/**
 *
 * @author stefan
 */
@RestController
@RequestMapping("/documenttypes")
public class DocumentTypeRestService {

    @Autowired
    private DocumentTypeService documentService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity create(@RequestHeader("X-Authorization") String token, @RequestBody DocumentTypeRequest documentRequest) throws BadRequestException {
        User authenticatedUser = userService.getAuthenticatedUser(token);
        DocumentType documentType = documentService.createDocumentType(createDocumentTypeFromRequest(documentRequest, authenticatedUser.getCompany()));

        DocumentTypeResponse documentTypeResponse = new DocumentTypeResponse(documentType);
        return ResponseEntity.ok(createSuccessResponse(documentTypeResponse));
    }

    @GetMapping
    public ResponseEntity getAll(@RequestHeader("X-Authorization") String token) {
        User authenticatedUser = userService.getAuthenticatedUser(token);
//        List<DocumentType> allDocumentTypes = documentService.getAllDocumentTypes();
        List<DocumentType> documentTypes = authenticatedUser.getCompany().getDocumentTypes();

        List<DocumentTypeResponse> documentTypeResponses = DocumentTypeResponse.getDocumentTypeResponseList(documentTypes);
        return ResponseEntity.ok(createSuccessResponse(documentTypeResponses));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getOne(@RequestHeader("X-Authorization") String token, @PathVariable("id") Long id) throws BadRequestException {
        User authenticatedUser = userService.getAuthenticatedUser(token);
        DocumentType documentType = documentService.getDocumentType(id);
        
        if (!documentType.getCompany().equals(authenticatedUser.getCompany())) {
            throw new BadRequestException("Your company does not have specified document type.");
        }

        DocumentTypeResponse documentTypeResponse = new DocumentTypeResponse(documentType);
        return ResponseEntity.ok(createSuccessResponse(documentTypeResponse));
    }

    private DocumentType createDocumentTypeFromRequest(DocumentTypeRequest documentRequest, Company company) {
        DocumentType documentType = new DocumentType();
        documentType.setName(documentRequest.getName());
        documentType.setCompany(company);
        documentType.setDescriptors(documentRequest.getDescriptors().stream()
                .map(e -> new Descriptor(e))
                .collect(Collectors.toList()));
        return documentType;
    }

}
