package rs.ac.bg.fon.silab.dms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.silab.dms.core.model.User;
import rs.ac.bg.fon.silab.dms.core.service.UserService;
import rs.ac.bg.fon.silab.dms.security.exception.UnknownUserException;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    private final BCryptPasswordEncoder encoder;

    @Autowired
    public CustomAuthenticationProvider(UserService userService, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        User user = userService.getUser(username);

        if (user == null) {
            throw new UnknownUserException("Could not find user with username: " + username);
        }
        validatePassword((String) authentication.getCredentials(), user.getPassword());
        return new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        boolean result = false;

        if (authentication.equals(UsernamePasswordAuthenticationToken.class))
            result = true;

        return result;
    }

    void validatePassword(String provided, String expected) {
        if (!encoder.matches(provided, expected)) {
            throw new UnknownUserException("Wrong username or password.");
        }
    }
}
