/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.documenttype;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.core.model.Descriptor;
import rs.ac.bg.fon.silab.dms.core.model.DocumentType;
import rs.ac.bg.fon.silab.dms.core.service.DocumentTypeService;
import rs.ac.bg.fon.silab.dms.rest.services.documenttype.dto.DocumentTypeRequest;
import rs.ac.bg.fon.silab.dms.rest.services.documenttype.dto.DocumentTypeResponse;

/**
 *
 * @author stefan
 */
@RestController
@RequestMapping("/documents")
public class DocumentTypeRestService {

    @Autowired
    private DocumentTypeService documentService;

    @PostMapping
    public ResponseEntity<DocumentTypeResponse> create(RequestEntity<DocumentTypeRequest> request) throws BadRequestException {
        DocumentTypeRequest documentRequest = request.getBody();
        DocumentType documentType = documentService.createDocumentType(createDocumentTypeFromRequest(documentRequest));

        DocumentTypeResponse documentTypeResponse = new DocumentTypeResponse(documentType);
        return ResponseEntity.ok(documentTypeResponse);
    }

    @GetMapping
    public ResponseEntity<List<DocumentTypeResponse>> getAll() {
        List<DocumentType> allDocumentTypes = documentService.getAllDocumentTypes();

        List<DocumentTypeResponse> documentTypeResponses = DocumentTypeResponse.getDocumentTypeResponseList(allDocumentTypes);
        return ResponseEntity.ok(documentTypeResponses);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DocumentTypeResponse> getOne(@PathVariable("id") Long id) throws BadRequestException {
        DocumentType documentType = documentService.getDocumentType(id);

        DocumentTypeResponse documentTypeResponse = new DocumentTypeResponse(documentType);
        return ResponseEntity.ok(documentTypeResponse);
    }

    private DocumentType createDocumentTypeFromRequest(DocumentTypeRequest documentRequest) {
        DocumentType documentType = new DocumentType();
        documentType.setName(documentRequest.getName());
        documentType.setDescriptors(documentRequest.getDescriptors().stream()
                .map(e -> new Descriptor(e))
                .collect(Collectors.toList()));
        return documentType;
    }

}
