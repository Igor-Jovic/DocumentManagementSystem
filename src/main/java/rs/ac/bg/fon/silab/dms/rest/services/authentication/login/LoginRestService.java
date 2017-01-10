package rs.ac.bg.fon.silab.dms.rest.services.authentication.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.AuthenticationRestService;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.login.dto.LoginRequest;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.registration.dto.RegistrationRequest;
import rs.ac.bg.fon.silab.dms.security.CustomAuthenticationProvider;
import rs.ac.bg.fon.silab.dms.security.TokenAuthenticationService;

import java.time.LocalDateTime;

@RestController
public class LoginRestService extends AuthenticationRestService {
    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Autowired
    TokenAuthenticationService tokenAuthenticationService;

    public void setAuthenticationProvider(CustomAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    public void setTokenAuthenticationService(TokenAuthenticationService tokenAuthenticationService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) throws BadRequestException {
        validateRequest(loginRequest);
        String token = handleAuthentication(loginRequest);
        return (ResponseEntity) ResponseEntity.ok(token);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity logout(@RequestHeader("X-Authorization") String token) throws BadRequestException {
        tokenAuthenticationService.removeAuthentication(token);
        return (ResponseEntity) ResponseEntity.ok("LoggedOut");
    }

    private String handleAuthentication(LoginRequest loginRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password);
        authentication = authenticationProvider.authenticate(authentication);
        return tokenAuthenticationService.saveAuthentication(authentication, LocalDateTime.now());
    }

    private void validateRequest(LoginRequest loginRequest) throws BadRequestException {
        if (!loginRequest.isValid()) {
            throw new BadRequestException("A problem occured. In order to login you need to provide username and password.");
        }
    }
}
