package rs.ac.bg.fon.silab.dms.core.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "document_type")
public class DocumentType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "document_type_descriptor", joinColumns = {
        @JoinColumn(name = "DOCUMENT_TYPE_ID")},
            inverseJoinColumns = {
                @JoinColumn(name = "DESCRIPTOR_ID")})
    private List<Descriptor> descriptors;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Document> documents;

    public DocumentType() {
    }

    public DocumentType(String name, Company company) {
        this.name = name;
        this.company = company;
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

    public List<Descriptor> getDescriptors() {
        return descriptors;
    }

    public void setDescriptors(List<Descriptor> descriptors) {
        this.descriptors = descriptors;
    }
    
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DocumentType document = (DocumentType) o;

        return id.equals(document.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
