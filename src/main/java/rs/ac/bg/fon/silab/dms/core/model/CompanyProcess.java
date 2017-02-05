package rs.ac.bg.fon.silab.dms.core.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "process")
public class CompanyProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "IS_PRIMITIVE")
    private boolean isPrimitive;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentProcess")
    private List<CompanyProcess> childProcesses;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_ID")
    private CompanyProcess parentProcess;

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

    public List<CompanyProcess> getChildProcesses() {
        return childProcesses;
    }

    public void setChildProcesses(List<CompanyProcess> childProcesses) {
        this.childProcesses = childProcesses;
    }

    public CompanyProcess getParentProcess() {
        return parentProcess;
    }

    public void setParentProcess(CompanyProcess parentProcess) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanyProcess process = (CompanyProcess) o;

        return id.equals(process.id) && company.equals(process.company);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + company.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Process{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isPrimitive=" + isPrimitive +
                ", childProcesses=" + childProcesses +
                ", parentProcess=" + parentProcess +
                ", company=" + company +
                ", activities=" + activities +
                '}';
    }

    boolean containsProcess(Long id) {
        return this.id.equals(id) &&
                (this.id.equals(id) || childProcesses.stream().anyMatch(companyProcess -> containsProcess(id)));
    }
}
