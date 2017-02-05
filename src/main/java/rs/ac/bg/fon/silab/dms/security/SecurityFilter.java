package rs.ac.bg.fon.silab.dms.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilter extends OncePerRequestFilter {

    final TokenAuthenticationService tokenAuthenticationService = TokenAuthenticationService.getInstance();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String AUTHORIZATION_HEADER = "X-Authorization";
        String token = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        AuthenticationData authenticationData = tokenAuthenticationService.getAuthenticationData(token);
        if (authenticationData != null) {
            SecurityContextHolder.getContext().setAuthentication(authenticationData.getAuthentication());
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
