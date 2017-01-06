package rs.ac.bg.fon.silab.dms.dataaccess.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DocumentDescriptorAssociation> documentDescriptorAssociations;

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

    public List<DocumentDescriptorAssociation> getDocumentDescriptorAssociations() {
        return documentDescriptorAssociations;
    }

    public void setDocumentDescriptorAssociations(List<DocumentDescriptorAssociation> documentDescriptorAssociations) {
        this.documentDescriptorAssociations = documentDescriptorAssociations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Document document = (Document) o;

        return id.equals(document.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", documentDescriptorAssociations=" + documentDescriptorAssociations +
                '}';
    }
}
