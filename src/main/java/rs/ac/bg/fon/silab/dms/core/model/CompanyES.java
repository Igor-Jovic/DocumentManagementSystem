/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.core.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 *
 * @author stefan
 */
@Document(indexName = "dms", type = "company")
public class CompanyES {

    @Id
    private Long id;
    private String name;
    private String description;

    public CompanyES() {
    }

    public CompanyES(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.description = company.getDescription();
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


}
