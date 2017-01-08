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
    @JoinTable(name = "document_type_descriptor", joinColumns = {@JoinColumn(name = "DOCUMENT_TYPE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "DESCRIPTOR_ID")})
    private List<Descriptor> descriptors;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocumentType document = (DocumentType) o;

        return id.equals(document.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
