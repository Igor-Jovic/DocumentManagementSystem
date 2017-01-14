package rs.ac.bg.fon.silab.dms.rest.services.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.ac.bg.fon.silab.dms.core.service.CompanyService;
import rs.ac.bg.fon.silab.dms.core.service.UserService;
import rs.ac.bg.fon.silab.dms.security.CustomAuthenticationProvider;
import rs.ac.bg.fon.silab.dms.security.TokenAuthenticationService;

@RequestMapping("/auth")
public class AuthenticationRestService {

    @Autowired
    protected UserService userService;

    @Autowired
    protected CustomAuthenticationProvider authenticationProvider;

    @Autowired
    protected TokenAuthenticationService tokenAuthenticationService;

    public AuthenticationRestService(UserService userService, CustomAuthenticationProvider customAuthenticationProvider, TokenAuthenticationService tokenAuthenticationService) {
        this.authenticationProvider = customAuthenticationProvider;
        this.userService = userService;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }
}
