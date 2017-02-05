/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.core.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class DocumentDescriptorAssociationPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "document")
    private long document;
    @Basic(optional = false)
    @NotNull
    @Column(name = "descriptor")
    private long descriptor;

    public DocumentDescriptorAssociationPK() {
    }

    public DocumentDescriptorAssociationPK(long document, long descriptor) {
        this.document = document;
        this.descriptor = descriptor;
    }

    public long getDocument() {
        return document;
    }

    public void setDocument(long document) {
        this.document = document;
    }

    public long getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(long descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (long) document;
        hash += (long) descriptor;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentDescriptorAssociationPK)) {
            return false;
        }
        DocumentDescriptorAssociationPK other = (DocumentDescriptorAssociationPK) object;
        if (this.document != other.document) {
            return false;
        }
        return this.descriptor == other.descriptor;
    }

    @Override
    public String toString() {
        return "DocumentDescriptorAssociationPK{" + "document=" + document + ", descriptor=" + descriptor + '}';
    }

}
