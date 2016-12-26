package rs.ac.bg.fon.silab.dms.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "process")
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "IS_PRIMITIVE")
    private boolean isPrimitive;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentProcess")
    private List<Process> childProcesses;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_ID")
    private Process parentProcess;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentProcess")
    private List<Activity> activities;

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

    public boolean isPrimitive() {
        return isPrimitive;
    }

    public void setPrimitive(boolean primitive) {
        isPrimitive = primitive;
    }

    public List<Process> getChildProcesses() {
        return childProcesses;
    }

    public void setChildProcesses(List<Process> childProcesses) {
        this.childProcesses = childProcesses;
    }

    public Process getParentProcess() {
        return parentProcess;
    }

    public void setParentProcess(Process parentProcess) {
        this.parentProcess = parentProcess;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
