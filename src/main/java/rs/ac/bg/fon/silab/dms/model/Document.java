package rs.ac.bg.fon.silab.dms.model;

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
}
