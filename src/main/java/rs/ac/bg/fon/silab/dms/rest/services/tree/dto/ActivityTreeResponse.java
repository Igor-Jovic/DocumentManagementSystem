/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.tree.dto;

import rs.ac.bg.fon.silab.dms.core.model.Activity;

/**
 * @author stefan
 */
public class ActivityTreeResponse {

    private Long id;
    private String name;
    private DocumentTypeResponse inputDocumentType;
    private DocumentTypeResponse outputDocumentType;

    public ActivityTreeResponse() {
    }

    ActivityTreeResponse(Activity activity) {
        this.id = activity.getId();
        this.name = activity.getName();
        if (activity.getInputDocumentType() != null) {
            this.inputDocumentType = new DocumentTypeResponse(activity.getInputDocumentType().getId(), activity.getInputDocumentType().getName());

        }
        if (activity.getOutputDocumentType() != null) {
            this.outputDocumentType = new DocumentTypeResponse(activity.getOutputDocumentType().getId(), activity.getOutputDocumentType().getName());
        }
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

    public DocumentTypeResponse getInputDocumentType() {
        return inputDocumentType;
    }

    public void setInputDocumentType(DocumentTypeResponse inputDocumentType) {
        this.inputDocumentType = inputDocumentType;
    }

    public DocumentTypeResponse getOutputDocumentType() {
        return outputDocumentType;
    }

    public void setOutputDocumentType(DocumentTypeResponse outputDocumentType) {
        this.outputDocumentType = outputDocumentType;
    }
}
