/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.tree.dto;

import java.util.List;
import java.util.stream.Collectors;
import rs.ac.bg.fon.silab.dms.core.model.CompanyProcess;

/**
 *
 * @author stefan
 */
public class TreeResponse {
    
    private Long id;
    private String name;
    private boolean isPrimitive;
    private List<TreeResponse> childProcesses;
    private List<ActivityTreeResponse> activities;

    public TreeResponse() {
    }

    public TreeResponse(CompanyProcess companyProcess) {
        this.id = companyProcess.getId();
        this.name = companyProcess.getName();
        this.isPrimitive = companyProcess.isPrimitive();
        if (companyProcess.getChildProcesses() != null) {
            this.childProcesses = companyProcess.getChildProcesses().stream()
                    .map(e -> new TreeResponse(e))
                    .collect(Collectors.toList());
        }
        if (companyProcess.getActivities() != null) {
            this.activities = companyProcess.getActivities().stream()
                    .map(e -> new ActivityTreeResponse(e))
                    .collect(Collectors.toList());
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

    public boolean isIsPrimitive() {
        return isPrimitive;
    }

    public void setIsPrimitive(boolean isPrimitive) {
        this.isPrimitive = isPrimitive;
    }

    public List<TreeResponse> getChildProcesses() {
        return childProcesses;
    }

    public void setChildProcesses(List<TreeResponse> childProcesses) {
        this.childProcesses = childProcesses;
    }

    public List<ActivityTreeResponse> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityTreeResponse> activities) {
        this.activities = activities;
    }
    
}
