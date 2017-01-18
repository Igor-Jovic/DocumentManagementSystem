///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package rs.ac.bg.fon.silab.dms.rest.services.tree.dto;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import rs.ac.bg.fon.silab.dms.core.model.Document;
//
///**
// *
// * @author stefan
// */
//public class DocumentTreeResponse {
//
//    private Long id;
//    private DocumentTypeTreeResponse documentType;
//    private List<DocumentDescriptorTreeResponse> documentDescriptors;
//
//    public DocumentTreeResponse() {
//    }
//
//    public DocumentTreeResponse(Document document) {
//        this.id = document.getId();
//        this.documentType = new DocumentTypeTreeResponse(document.getDocumentType());
//        if (document.getDocumentDescriptorAssociationList() != null) {
//            this.documentDescriptors = document.getDocumentDescriptorAssociationList().stream()
//                    .map(e -> new DocumentDescriptorTreeResponse(e))
//                    .collect(Collectors.toList());
//        }
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public DocumentTypeTreeResponse getDocumentType() {
//        return documentType;
//    }
//
//    public void setDocumentType(DocumentTypeTreeResponse documentType) {
//        this.documentType = documentType;
//    }
//
//    public List<DocumentDescriptorTreeResponse> getDocumentDescriptors() {
//        return documentDescriptors;
//    }
//
//    public void setDocumentDescriptors(List<DocumentDescriptorTreeResponse> documentDescriptors) {
//        this.documentDescriptors = documentDescriptors;
//    }
//
//}
