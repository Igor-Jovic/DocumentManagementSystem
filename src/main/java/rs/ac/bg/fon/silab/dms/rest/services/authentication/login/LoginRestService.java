package rs.ac.bg.fon.silab.dms.rest.services.authentication.login;

import org.springframework.beans.factory.annotation.Autowired;
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
import rs.ac.bg.fon.silab.dms.rest.services.authentication.login.dto.LoginResponse;
import rs.ac.bg.fon.silab.dms.security.AuthenticationData;
import rs.ac.bg.fon.silab.dms.security.CustomAuthenticationProvider;
import rs.ac.bg.fon.silab.dms.security.TokenAuthenticationService;

import java.time.LocalDateTime;

import static rs.ac.bg.fon.silab.dms.rest.model.ApiResponse.createErrorResponse;
import static rs.ac.bg.fon.silab.dms.rest.model.ApiResponse.createSuccessResponse;

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
        LoginResponse loginResponse = handleAuthentication(loginRequest);
        return (ResponseEntity) ResponseEntity.ok(createSuccessResponse(loginResponse));
    }

    //TODO REWORK
    @PostMapping(value = "/logout")
    public ResponseEntity logout(@RequestHeader("X-Authorization") String token) throws BadRequestException {
        tokenAuthenticationService.removeAuthentication(token);
        return (ResponseEntity) ResponseEntity.ok(createErrorResponse("Logged out"));
    }

    private LoginResponse handleAuthentication(LoginRequest loginRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password);
        authentication = authenticationProvider.authenticate(authentication);
        AuthenticationData authenticationData = tokenAuthenticationService.saveAuthentication(authentication, LocalDateTime.now());
        return new LoginResponse((String) authentication.getPrincipal(), authenticationData.getRole(), authenticationData.getToken());
    }

    private void validateRequest(LoginRequest loginRequest) throws BadRequestException {
        if (!loginRequest.isValid()) {
            throw new BadRequestException("A problem occured. In order to login you need to provide username and password.");
        }

    }
}
