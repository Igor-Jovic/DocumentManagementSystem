/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.process.dto;

import rs.ac.bg.fon.silab.dms.core.model.CompanyProcess;

public class ProcessResponse {

    private Long id;
    private String name;
    private boolean isPrimitive;

    public ProcessResponse() {
    }

    public ProcessResponse(CompanyProcess process) {
        this.id = process.getId();
        this.name = process.getName();
        this.isPrimitive = process.isPrimitive();
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

}
