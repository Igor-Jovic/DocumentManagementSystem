/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.document.dto;

import rs.ac.bg.fon.silab.dms.core.model.Document;

import java.util.List;
import java.util.stream.Collectors;

public class DocumentResponse {

    private String description;
    private Long id;
    private String name;
    private Long documentTypeId;
    private List<DocumentDescriptorResponse> documentDescriptors;
    private String downloadLink;


    public DocumentResponse() {
    }

    public DocumentResponse(Document document) {
        this.id = document.getId();
        this.name = document.getDocumentType().getName();
        this.documentTypeId = document.getDocumentType().getId();
        this.documentDescriptors = document.getDocumentDescriptorAssociationList().stream()
                .map(DocumentDescriptorResponse::new)
                .collect(Collectors.toList());
        this.description = document.getDocumentDescriptorAssociationList().stream()
                .map(documentDescriptorAssociation -> String.format("%s : %s", documentDescriptorAssociation.getDescriptor().getName(), documentDescriptorAssociation.getValue()))
                .collect(Collectors.joining(", "));
        this.downloadLink = buildDownloadLink(document.getFileName());
    }

    private String buildDownloadLink(String fileName) {
        return String.format("/documents/file/%s", fileName);
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
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

    public List<DocumentDescriptorResponse> getDocumentDescriptors() {
        return documentDescriptors;
    }

    public void setDocumentDescriptors(List<DocumentDescriptorResponse> documentDescriptors) {
        this.documentDescriptors = documentDescriptors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<DocumentResponse> getDocumentResponseList(List<Document> documents) {
        return documents.stream()
                .map(DocumentResponse::new)
                .collect(Collectors.toList());
    }

}
