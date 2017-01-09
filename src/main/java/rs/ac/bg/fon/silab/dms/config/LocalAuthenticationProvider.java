package rs.ac.bg.fon.silab.dms.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;

/**
 * Authenticates a user against a local database
 *
 * @author Sinisa Komarica
 */
public class LocalAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // This is just an example
        // TODO implement check against the database once the User-related functionality is completed

        return new UsernamePasswordAuthenticationToken(
                (String) authentication.getPrincipal(),
                (String) authentication.getCredentials(),
                Arrays.asList(new SimpleGrantedAuthority("role")));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        boolean result = false;

        if (authentication.equals(UsernamePasswordAuthenticationToken.class))
            result = true;

        return result;
    }
}
