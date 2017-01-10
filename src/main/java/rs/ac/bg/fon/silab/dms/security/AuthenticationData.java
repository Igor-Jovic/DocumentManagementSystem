package rs.ac.bg.fon.silab.dms.security;

import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;

class AuthenticationData {
    private Authentication authentication;
    private LocalDateTime authenticationTime;

    public AuthenticationData(Authentication authentication, LocalDateTime authenticationTime) {
        this.authentication = authentication;
        this.authenticationTime = authenticationTime;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public LocalDateTime getAuthenticationTime() {
        return authenticationTime;
    }
}
