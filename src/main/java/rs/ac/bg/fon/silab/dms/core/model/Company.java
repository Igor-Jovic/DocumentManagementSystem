package rs.ac.bg.fon.silab.dms.core.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", unique = true)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<CompanyProcess> processes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<User> employees;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<DocumentType> documentTypes;

    public Company() {
    }

    public Company(String name) {
        this.name = name;
        employees = new ArrayList<>();
    }
    
    public Company(String name, String description) {
        this.name = name;
        this.description = description;
        employees = new ArrayList<>();
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
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CompanyProcess> getProcesses() {
        return processes;
    }

    public void setProcesses(List<CompanyProcess> processes) {
        this.processes = processes;
    }

    public List<User> getEmployees() {
        return employees;
    }

    public void setEmployees(List<User> employees) {
        this.employees = employees;
    }

    public List<DocumentType> getDocumentTypes() {
        return documentTypes;
    }

    public void setDocumentTypes(List<DocumentType> documentTypes) {
        this.documentTypes = documentTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Company company = (Company) o;

        return id.equals(company.id) && name.equals(company.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Company{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", processes=" + processes
                + ", employees=" + employees
                + '}';
    }

    public boolean hasProcess(Long id) {
        return processes.stream()
                .anyMatch(companyProcess -> companyProcess.containsProcess(id));
    }

}