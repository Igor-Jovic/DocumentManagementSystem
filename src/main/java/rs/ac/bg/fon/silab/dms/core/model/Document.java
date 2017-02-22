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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "INPUT_FOR_ACTIVITY")
    private Activity inputForActivity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OUTPUT_FOR_ACTIVITY")
    private Activity outputForActivity;

    @Column(name = "FILE_NAME")
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Activity getInputForActivity() {
        return inputForActivity;
    }

    public void setInputForActivity(Activity inputForActivity) {
        this.inputForActivity = inputForActivity;
    }

    public Activity getOutputForActivity() {
        return outputForActivity;
    }

    public void setOutputForActivity(Activity outputForActivity) {
        this.outputForActivity = outputForActivity;
    }

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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Document document = (Document) o;

        return id.equals(document.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
