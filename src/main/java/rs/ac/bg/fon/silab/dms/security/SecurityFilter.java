package rs.ac.bg.fon.silab.dms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class SecurityFilter extends OncePerRequestFilter {

    TokenAuthenticationService tokenAuthenticationService = TokenAuthenticationService.getInstance();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //get the token
        String token = httpServletRequest.getHeader("X-Authorization");
        //get authentication from TokenHandler
        Authentication authentication = tokenAuthenticationService.getAuthenticationByToken(token);

        //SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


//    @Autowired
//    private AuthenticationProvider authenticationProvider;
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        System.out.println("FilterCalled");
//
//        //TODO: revisit after token service implementation
//        //this is just for development, it will authorize every request with role role
//        //instead token should be extracted from header and used to
//        //check for authentication in token service and if found
//        //that authentication should be placed on context
//        Authentication authentication = new UsernamePasswordAuthenticationToken(
//                "any",
//                "any",
//                Arrays.asList(new SimpleGrantedAuthority("role")));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        System.out.println(authentication.isAuthenticated());
//
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    public void destroy() {
//    }
}
