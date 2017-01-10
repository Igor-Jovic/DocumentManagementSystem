package rs.ac.bg.fon.silab.dms.security;

import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenAuthenticationService {

    private static TokenAuthenticationService ourInstance = new TokenAuthenticationService();
    private Map<String, Authentication> cachedAuthenticationData;

    private TokenAuthenticationService() {
        cachedAuthenticationData = new HashMap<>();
    }

    public static TokenAuthenticationService getInstance() {
        return ourInstance;
    }

    public Authentication getAuthenticationByToken(String token) {
        return cachedAuthenticationData.get(token);
    }

    public String saveAuthentication(Authentication authentication) {
        String token = generateToken();
        cachedAuthenticationData.put(token, authentication);
        return token;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }


}
