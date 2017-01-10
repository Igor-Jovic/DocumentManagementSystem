package rs.ac.bg.fon.silab.dms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.silab.dms.core.model.User;
import rs.ac.bg.fon.silab.dms.core.repository.UserRepository;
import rs.ac.bg.fon.silab.dms.security.exception.UnknownUserException;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {


    private final UserRepository userRepository;

    @Autowired
    public CustomAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("called auth provider");
        String username = (String) authentication.getPrincipal();
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UnknownUserException("Could not find user with username: " + username);
        }

        return new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
    }

//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }

    @Override
    public boolean supports(Class<?> authentication) {
        boolean result = false;

        if (authentication.equals(UsernamePasswordAuthenticationToken.class))
            result = true;

        return result;
    }

//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//
//        // This is just an example
//        // TODO implement check against the database once the User-related functionality is completed
//        //put authentication to map
//        return new UsernamePasswordAuthenticationToken(
//                (String) authentication.getPrincipal(),
//                (String) authentication.getCredentials(),
//                Arrays.asList(new SimpleGrantedAuthority("role")));
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        boolean result = false;
//
//        if (authentication.equals(UsernamePasswordAuthenticationToken.class))
//            result = true;
//
//        return result;
//    }
}
