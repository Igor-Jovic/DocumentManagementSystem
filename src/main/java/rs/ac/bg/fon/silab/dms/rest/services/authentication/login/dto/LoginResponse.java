package rs.ac.bg.fon.silab.dms.rest.services.authentication.login.dto;


public class LoginResponse {
    public final String username;
    public final String userRole;
    public final String token;

    public LoginResponse(String username, String userRole, String token) {
        this.username = username;
        this.userRole = userRole;
        this.token = token;
    }
}
