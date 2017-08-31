/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.core.model;

/**
 *
 * @author stefan
 */
public class UserES {

    private Long id;
    private String username;
    private String companyName;
    private String role;

    public UserES() {
    }

    public UserES(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        if (user.getCompany() != null) {
            this.companyName = user.getCompany().getName();
        }
        if (user.getRole() != null) {
            this.role = user.getRole().name();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
