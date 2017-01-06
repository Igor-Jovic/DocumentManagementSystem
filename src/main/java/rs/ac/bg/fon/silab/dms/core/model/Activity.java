package rs.ac.bg.fon.silab.dms.core.model;

import javax.persistence.*;

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
