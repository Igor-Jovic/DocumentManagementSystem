package rs.ac.bg.fon.silab.dms.rest.services.authentication.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.core.model.User;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.login.dto.LoginRequest;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.login.dto.LoginResponse;
import rs.ac.bg.fon.silab.dms.security.CustomAuthenticationProvider;
import rs.ac.bg.fon.silab.dms.security.TokenAuthenticationService;

@RestController
public class LoginRestService {

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;
    TokenAuthenticationService tokenAuthenticationService = TokenAuthenticationService.getInstance();

    public void setAuthenticationProvider(CustomAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @PostMapping(value = "/login")
    public ResponseEntity register(@RequestBody LoginRequest loginRequest) throws BadRequestException {
        System.out.println(loginRequest.username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password);
        authentication = authenticationProvider.authenticate(authentication);
        String token = tokenAuthenticationService.saveAuthentication(authentication);
        return (ResponseEntity) ResponseEntity.ok(token);
    }


}
