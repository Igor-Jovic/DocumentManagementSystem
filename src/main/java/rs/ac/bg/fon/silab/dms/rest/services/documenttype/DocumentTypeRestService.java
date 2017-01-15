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
import rs.ac.bg.fon.silab.dms.core.model.Descriptor;
import rs.ac.bg.fon.silab.dms.core.model.DocumentType;
import rs.ac.bg.fon.silab.dms.core.service.DocumentTypeService;
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

    @PostMapping
    public ResponseEntity create(@RequestBody DocumentTypeRequest documentRequest) throws BadRequestException {
        DocumentType documentType = documentService.createDocumentType(createDocumentTypeFromRequest(documentRequest));

        DocumentTypeResponse documentTypeResponse = new DocumentTypeResponse(documentType);
        return ResponseEntity.ok(createSuccessResponse(documentTypeResponse));
    }

    @GetMapping
    public ResponseEntity getAll() {
        List<DocumentType> allDocumentTypes = documentService.getAllDocumentTypes();

        List<DocumentTypeResponse> documentTypeResponses = DocumentTypeResponse.getDocumentTypeResponseList(allDocumentTypes);
        return ResponseEntity.ok(createSuccessResponse(documentTypeResponses));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getOne(@PathVariable("id") Long id) throws BadRequestException {
        DocumentType documentType = documentService.getDocumentType(id);

        DocumentTypeResponse documentTypeResponse = new DocumentTypeResponse(documentType);
        return ResponseEntity.ok(createSuccessResponse(documentTypeResponse));
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
