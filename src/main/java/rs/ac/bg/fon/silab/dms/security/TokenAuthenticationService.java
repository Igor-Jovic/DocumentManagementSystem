package rs.ac.bg.fon.silab.dms.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenAuthenticationService {

    @Value("#{'${auth.token.timeToLive}'}")
    private int tokenLifeInMinutes;

    private static TokenAuthenticationService instance = new TokenAuthenticationService();
    private Map<String, AuthenticationData> cachedAuthenticationData;

    private TokenAuthenticationService() {
        cachedAuthenticationData = new HashMap<>();
    }

    public static TokenAuthenticationService getInstance() {
        return instance;
    }

    public AuthenticationData getAuthenticationByToken(String token) {
        AuthenticationData authenticationData = cachedAuthenticationData.get(token);
        if (authenticationData == null) {
            return null;
        }
        if (!authenticationDataIsValid(authenticationData)) {
            cachedAuthenticationData.remove(token);
            return null;
        }
        return authenticationData;
    }

    public String saveAuthentication(Authentication authentication, LocalDateTime localDateTime) {
        String token = generateToken();
        cachedAuthenticationData.put(token, new AuthenticationData(authentication, localDateTime));
        return token;
    }

    public void removeAuthentication(String token) {
        cachedAuthenticationData.remove(token);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private boolean authenticationDataIsValid(AuthenticationData authenticationData) {
        LocalDateTime authenticationTime = authenticationData.getAuthenticationTime();
        if (authenticationTime.plusMinutes(tokenLifeInMinutes).isBefore(LocalDateTime.now())) {
            return false;
        }
        return true;
    }
}
