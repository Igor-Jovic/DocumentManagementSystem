package rs.ac.bg.fon.silab.dms.rest.services.authentication.login.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class LoginResponse {
    public String username;
    public String userRole;
    public String token;

    public LoginResponse(String username, String userRole, String token) {
        this.username = username;
        this.userRole = userRole;
        this.token = token;
    }
}
