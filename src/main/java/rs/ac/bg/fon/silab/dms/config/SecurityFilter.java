package rs.ac.bg.fon.silab.dms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Slavko Komarica
 */
public class SecurityFilter implements Filter {


    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("FilterCalled");

        //TODO: revisit after token service implementation
        //this is just for development, it will authorize every request with role role
        //instead token should be extracted from header and used to
        //check for authentication in token service and if found
        //that authentication should be placed on context
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "any",
                "any",
                Arrays.asList(new SimpleGrantedAuthority("role")));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println(authentication.isAuthenticated());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
