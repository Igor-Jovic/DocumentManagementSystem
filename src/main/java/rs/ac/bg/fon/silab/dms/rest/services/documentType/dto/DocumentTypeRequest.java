/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.documentType.dto;

import java.util.List;

/**
 *
 * @author stefan
 */
public class DocumentTypeRequest {
    
    private String name;
    private List<String> descriptors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDescriptors() {
        return descriptors;
    }

    public void setDescriptors(List<String> descriptors) {
        this.descriptors = descriptors;
    }
    
}
