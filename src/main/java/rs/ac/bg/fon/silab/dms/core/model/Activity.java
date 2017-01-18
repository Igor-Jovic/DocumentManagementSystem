package rs.ac.bg.fon.silab.dms.core.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @OneToOne
    @JoinColumn(name = "INPUT_DOCUMENT_ID")
    private DocumentType inputDocumentType;

    @OneToOne
    @JoinColumn(name = "OUTPUT_DOCUMENT_ID")
    private DocumentType outputDocumentTypes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_PROCESS_ID")
    private CompanyProcess parentProcess;

    @OneToMany(mappedBy = "inputForActivity")
    private List<Document> inputDocuments;

    @OneToMany(mappedBy = "outputForActivity")
    private List<Document> outputDocuments;

    public Activity(DocumentType inputDocumentType, DocumentType outputDocumentTypes, CompanyProcess process, String name) {
        this.name = name;
        this.inputDocumentType = inputDocumentType;
        this.outputDocumentTypes = outputDocumentTypes;
        this.parentProcess = process;
        inputDocuments = new ArrayList<>();
        outputDocuments = new ArrayList<>();
    }

    public List<Document> getInputDocuments() {
        return inputDocuments;
    }

    public void setInputDocuments(List<Document> inputDocuments) {
        this.inputDocuments = inputDocuments;
    }

    public DocumentType getOutputDocumentTypes() {
        return outputDocumentTypes;
    }

    public void setOutputDocumentTypes(DocumentType outputDocumentTypes) {
        this.outputDocumentTypes = outputDocumentTypes;
    }

    public void setOutputDocuments(List<Document> outputDocuments) {
        this.outputDocuments = outputDocuments;
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

    public DocumentType getInputDocumentType() {
        return inputDocumentType;
    }

    public void setInputDocumentType(DocumentType inputDocumentType) {
        this.inputDocumentType = inputDocumentType;
    }

    public DocumentType getOutputDocuments() {
        return outputDocumentTypes;
    }

    public void setOutputDocuments(DocumentType outputDocuments) {
        this.outputDocumentTypes = outputDocuments;
    }

    public CompanyProcess getParentProcess() {
        return parentProcess;
    }

    public void setParentProcess(CompanyProcess parentProcess) {
        this.parentProcess = parentProcess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        if (!id.equals(activity.id)) return false;
        return parentProcess.equals(activity.parentProcess);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + parentProcess.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentProcess=" + parentProcess +
                '}';
    }
}
