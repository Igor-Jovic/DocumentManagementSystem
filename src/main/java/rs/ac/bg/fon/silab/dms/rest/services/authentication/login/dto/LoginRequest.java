package rs.ac.bg.fon.silab.dms.rest.services.authentication.login.dto;


import static org.springframework.util.StringUtils.isEmpty;

public class LoginRequest {
    public String username;
    public String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean isValid() {
        return !isEmpty(username)
                && !isEmpty(password);
    }


}
