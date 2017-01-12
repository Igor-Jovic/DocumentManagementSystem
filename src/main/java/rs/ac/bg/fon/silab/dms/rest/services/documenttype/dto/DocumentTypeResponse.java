/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.documenttype.dto;

import java.util.List;
import java.util.stream.Collectors;
import rs.ac.bg.fon.silab.dms.core.model.DocumentType;

/**
 *
 * @author stefan
 */
public class DocumentTypeResponse {
    
    private Long id;
    private String name;
    private List<DescriptorResponse> descriptors;

    public DocumentTypeResponse() {
    }

    public DocumentTypeResponse(DocumentType documentType) {
        this.id = documentType.getId();
        this.name = documentType.getName();
        this.descriptors = documentType.getDescriptors().stream()
                .map(e -> new DescriptorResponse(e))
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
    
    public static List<DocumentTypeResponse> getDocumentTypeResponseList(List<DocumentType> documentTypes) {
        return documentTypes.stream()
                .map(e -> new DocumentTypeResponse(e))
                .collect(Collectors.toList());
    }

    public List<DescriptorResponse> getDescriptors() {
        return descriptors;
    }

    public void setDescriptors(List<DescriptorResponse> descriptors) {
        this.descriptors = descriptors;
    }
    
}
