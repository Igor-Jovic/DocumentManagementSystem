package rs.ac.bg.fon.silab.dms.core.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DOCUMENT_TYPE_ID", nullable = false)
    private DocumentType documentType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "document")
    private List<DocumentDescriptorAssociation> documentDescriptorAssociationList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public List<DocumentDescriptorAssociation> getDocumentDescriptorAssociationList() {
        return documentDescriptorAssociationList;
    }

    public void setDocumentDescriptorAssociationList(List<DocumentDescriptorAssociation> documentDescriptorAssociationList) {
        this.documentDescriptorAssociationList = documentDescriptorAssociationList;
    }
}
