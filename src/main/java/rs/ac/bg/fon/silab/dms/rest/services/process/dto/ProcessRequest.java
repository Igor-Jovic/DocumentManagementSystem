/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.process.dto;

public class ProcessRequest {

    private String name;
    private boolean primitive;
    private Long parentProcess;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public void setPrimitive(boolean isPrimitive) {
        this.primitive = isPrimitive;
    }

    public Long getParentProcess() {
        return parentProcess;
    }

    public void setParentProcess(Long parentProcess) {
        this.parentProcess = parentProcess;
    }

}
