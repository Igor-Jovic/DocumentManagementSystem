/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.document.dto;

import rs.ac.bg.fon.silab.dms.core.model.DocumentDescriptorAssociation;

class DocumentDescriptorResponse implements Comparable {

    private String descriptor;
    private String value;

    public DocumentDescriptorResponse() {
    }

    DocumentDescriptorResponse(DocumentDescriptorAssociation association) {
        this.descriptor = association.getDescriptor().getName();
        this.value = association.getValue();
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public int compareTo(Object o) {
        DocumentDescriptorResponse documentDescriptorResponse = (DocumentDescriptorResponse) o;
        return this.descriptor.compareTo(documentDescriptorResponse.descriptor);
    }
}
