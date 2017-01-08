/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.documentType;

import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.core.model.Descriptor;
import rs.ac.bg.fon.silab.dms.core.model.DocumentType;
import rs.ac.bg.fon.silab.dms.core.service.DocumentTypeService;
import rs.ac.bg.fon.silab.dms.rest.documentType.dto.DocumentTypeRequest;
import rs.ac.bg.fon.silab.dms.rest.documentType.dto.DocumentTypeResponse;

/**
 *
 * @author stefan
 */
@Component
@Path("/documents")
public class DocumentTypeRestService {

    @Autowired
    private DocumentTypeService documentService;

    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response create(DocumentTypeRequest documentRequest) throws BadRequestException {
        DocumentType documentType = documentService.createDocumentType(createDocumentTypeFromRequest(documentRequest));

        DocumentTypeResponse documentTypeResponse = new DocumentTypeResponse(documentType);
        return Response.ok(documentTypeResponse).build();
    }

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<DocumentType> allDocumentTypes = documentService.getAllDocumentTypes();
        
        List<DocumentTypeResponse> documentTypeResponses = DocumentTypeResponse.getDocumentTypeResponseList(allDocumentTypes);
        return Response.ok(documentTypeResponses).build();
    }

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getOne(@PathParam("id") Long id) throws BadRequestException {
        DocumentType documentType = documentService.getDocumentType(id);
        
        DocumentTypeResponse documentTypeResponse = new DocumentTypeResponse(documentType);
        return Response.ok(documentTypeResponse).build();
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
