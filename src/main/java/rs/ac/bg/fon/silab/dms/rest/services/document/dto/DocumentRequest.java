/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.document.dto;

import java.util.List;

/**
 *
 * @author stefan
 */
public class DocumentRequest {
    
    private Long documentTypeId;
    private List<DocumentDescriptorRequest> descriptorRequests;

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public List<DocumentDescriptorRequest> getDescriptorRequests() {
        return descriptorRequests;
    }

    public void setDescriptorRequests(List<DocumentDescriptorRequest> descriptorRequests) {
        this.descriptorRequests = descriptorRequests;
    }
    
}
