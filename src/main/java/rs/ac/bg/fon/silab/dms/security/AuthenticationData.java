package rs.ac.bg.fon.silab.dms.security;

import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;

public class AuthenticationData {
    private Authentication authentication;
    private LocalDateTime authenticationTime;
    private String token;


    AuthenticationData(String token, Authentication authentication, LocalDateTime authenticationTime) {
        this.authentication = authentication;
        this.authenticationTime = authenticationTime;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    LocalDateTime getAuthenticationTime() {
        return authenticationTime;
    }

    public String getRole() {
        return authentication.getAuthorities().toArray()[0].toString();
    }
}
