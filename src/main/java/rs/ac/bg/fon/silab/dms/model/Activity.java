package rs.ac.bg.fon.silab.dms.model;

import javax.persistence.*;
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
    private Document inputDocument;

    @OneToOne
    @JoinColumn(name = "OUTPUT_DOCUMENT_ID")
    private Document outputDocument;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_PROCESS_ID")
    private Process parentProcess;

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

    public Document getInputDocument() {
        return inputDocument;
    }

    public void setInputDocument(Document inputDocument) {
        this.inputDocument = inputDocument;
    }

    public Document getOutputDocument() {
        return outputDocument;
    }

    public void setOutputDocument(Document outputDocument) {
        this.outputDocument = outputDocument;
    }

    public Process getParentProcess() {
        return parentProcess;
    }

    public void setParentProcess(Process parentProcess) {
        this.parentProcess = parentProcess;
    }
}
