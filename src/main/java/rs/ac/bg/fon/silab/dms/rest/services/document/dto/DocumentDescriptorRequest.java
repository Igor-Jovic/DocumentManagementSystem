/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.document.dto;

/**
 *
 * @author stefan
 */
public class DocumentDescriptorRequest {
    
    private Long descriptorId;
    private String value;

    public Long getDescriptorId() {
        return descriptorId;
    }

    public void setDescriptorId(Long descriptorId) {
        this.descriptorId = descriptorId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
