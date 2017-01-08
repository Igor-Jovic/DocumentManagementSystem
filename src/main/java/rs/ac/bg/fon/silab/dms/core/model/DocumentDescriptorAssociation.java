package rs.ac.bg.fon.silab.dms.core.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "document_descriptor")
public class DocumentDescriptorAssociation implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DOCUMENT_ID")
    private Document document;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DESCRIPTOR_ID")
    private Descriptor descriptor;

    @Column(name = "VALUE")
    private String value;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocumentDescriptorAssociation that = (DocumentDescriptorAssociation) o;

        if (document != null ? !document.equals(that.document) : that.document != null) return false;
        return descriptor != null ? descriptor.equals(that.descriptor) : that.descriptor == null;

    }

    @Override
    public int hashCode() {
        int result = document != null ? document.hashCode() : 0;
        result = 31 * result + (descriptor != null ? descriptor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DocumentDescriptorAssociation{" +
                ", descriptor=" + descriptor +
                ", value='" + value + '\'' +
                '}';
    }
}
