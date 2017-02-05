package rs.ac.bg.fon.silab.dms.core.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "document_descriptor")
public class DocumentDescriptorAssociation implements Serializable {

    @EmbeddedId
    private DocumentDescriptorAssociationPK descriptorAssociationPK;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DOCUMENT_ID")
    private Document document;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DESCRIPTOR_ID")
    private Descriptor descriptor;

    @Column(name = "VALUE")
    private String value;

    public DocumentDescriptorAssociation() {
    }

    public DocumentDescriptorAssociation(DocumentDescriptorAssociationPK pk) {
        this.descriptorAssociationPK = pk;
    }

    public DocumentDescriptorAssociation(long document, long descriptor) {
        this.descriptorAssociationPK = new DocumentDescriptorAssociationPK(document, descriptor);
    }

    public DocumentDescriptorAssociation(long document, long descriptor, String value) {
        this.descriptorAssociationPK = new DocumentDescriptorAssociationPK(document, descriptor);
        this.value = value;
    }

    public DocumentDescriptorAssociation(Document document, Descriptor descriptor, String value) {
        this.descriptorAssociationPK = new DocumentDescriptorAssociationPK(document.getId(), descriptor.getId());
        this.document = document;
        this.descriptor = descriptor;
        this.value = value;
    }

    public DocumentDescriptorAssociationPK getDescriptorAssociationPK() {
        return descriptorAssociationPK;
    }

    public void setDescriptorAssociationPK(DocumentDescriptorAssociationPK descriptorAssociationPK) {
        this.descriptorAssociationPK = descriptorAssociationPK;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Descriptor getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(Descriptor descriptor) {
        this.descriptor = descriptor;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentDescriptorAssociation)) {
            return false;
        }
        DocumentDescriptorAssociation other = (DocumentDescriptorAssociation) object;
        if ((this.descriptorAssociationPK == null && other.descriptorAssociationPK != null)
                || (this.descriptorAssociationPK != null && !this.descriptorAssociationPK.equals(other.descriptorAssociationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = document != null ? document.hashCode() : 0;
        result = 31 * result + (descriptor != null ? descriptor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DocumentDescriptorAssociation{"
                + ", descriptor=" + descriptor
                + ", value='" + value + '\''
                + '}';
    }

}
