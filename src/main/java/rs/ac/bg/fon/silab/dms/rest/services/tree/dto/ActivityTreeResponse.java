///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package rs.ac.bg.fon.silab.dms.rest.services.tree.dto;
//
//import rs.ac.bg.fon.silab.dms.core.model.Activity;
//
///**
// *
// * @author stefan
// */
//public class ActivityTreeResponse {
//
//    private Long id;
//    private String name;
//    private DocumentTreeResponse inputDocument;
//    private DocumentTreeResponse outputDocument;
//
//    public ActivityTreeResponse() {
//    }
//
//    public ActivityTreeResponse(Activity activity) {
//        this.id = activity.getId();
//        this.name = activity.getName();
//        if (activity.getInputDocumentType() != null) {
//            this.inputDocument = new DocumentTreeResponse(activity.getInputDocumentType());
//        }
//        if (activity.getOutputDocumentTypes() != null) {
//            this.outputDocument = new DocumentTreeResponse(activity.getOutputDocumentTypes());
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
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public DocumentTreeResponse getInputDocumentType() {
//        return inputDocument;
//    }
//
//    public void setInputDocumentType(DocumentTreeResponse inputDocument) {
//        this.inputDocument = inputDocument;
//    }
//
//    public DocumentTreeResponse getOutputDocumentTypes() {
//        return outputDocument;
//    }
//
//    public void setOutputDocumentTypes(DocumentTreeResponse outputDocument) {
//        this.outputDocument = outputDocument;
//    }
//
//}
