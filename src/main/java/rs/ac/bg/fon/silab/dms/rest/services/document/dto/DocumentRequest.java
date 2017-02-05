/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.document.dto;

import java.util.List;

public class DocumentRequest {

    private Long id;
    private List<DocumentDescriptorRequest> descriptors;
    private boolean input;
    private Long activityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DocumentDescriptorRequest> getDescriptors() {
        return descriptors;
    }

    public void setDescriptors(List<DocumentDescriptorRequest> descriptors) {
        this.descriptors = descriptors;
    }

    public boolean isInput() {
        return input;
    }

    public void setInput(boolean input) {
        this.input = input;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

}
