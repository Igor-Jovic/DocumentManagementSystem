/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.document.dto;

import java.util.List;
import java.util.stream.Collectors;
import rs.ac.bg.fon.silab.dms.core.model.Document;

/**
 *
 * @author stefan
 */
public class DocumentResponse {

    private Long id;
    private String name;
    private Long documentTypeId;
    private List<DocumentDescriptorResponse> documentDescriptor;

    public DocumentResponse() {
    }

    public DocumentResponse(Document document) {
        this.id = document.getId();
        this.name = document.getDocumentType().getName();
        this.documentTypeId = document.getDocumentType().getId();
        this.documentDescriptor = document.getDocumentDescriptorAssociationList().stream()
                .map(e -> new DocumentDescriptorResponse(e))
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public List<DocumentDescriptorResponse> getDocumentDescriptor() {
        return documentDescriptor;
    }

    public void setDocumentDescriptor(List<DocumentDescriptorResponse> documentDescriptor) {
        this.documentDescriptor = documentDescriptor;
    }

    public static List<DocumentResponse> getDocumentResponseList(List<Document> documents) {
        return documents.stream()
                .map(e -> new DocumentResponse(e))
                .collect(Collectors.toList());
    }

}
