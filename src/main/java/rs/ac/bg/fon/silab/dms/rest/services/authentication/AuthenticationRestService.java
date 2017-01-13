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
    protected CompanyService companyService;

    @Autowired
    protected CustomAuthenticationProvider authenticationProvider;

    @Autowired
    protected TokenAuthenticationService tokenAuthenticationService;

    public void setAuthenticationProvider(CustomAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    public void setTokenAuthenticationService(TokenAuthenticationService tokenAuthenticationService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
