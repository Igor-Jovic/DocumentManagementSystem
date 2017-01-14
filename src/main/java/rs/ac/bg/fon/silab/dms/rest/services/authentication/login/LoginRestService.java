package rs.ac.bg.fon.silab.dms.rest.services.authentication.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.core.service.UserService;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.AuthenticationRestService;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.login.dto.LoginRequest;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.login.dto.LoginResponse;
import rs.ac.bg.fon.silab.dms.security.AuthenticationData;
import rs.ac.bg.fon.silab.dms.security.CustomAuthenticationProvider;
import rs.ac.bg.fon.silab.dms.security.TokenAuthenticationService;

import java.time.LocalDateTime;

import static rs.ac.bg.fon.silab.dms.rest.model.ApiResponse.createErrorResponse;
import static rs.ac.bg.fon.silab.dms.rest.model.ApiResponse.createSuccessResponse;
import static rs.ac.bg.fon.silab.dms.rest.services.authentication.AuthenticationMapper.authenticationDataToLoginResponse;

@RestController
public class LoginRestService extends AuthenticationRestService {

    public LoginRestService(UserService userService, CustomAuthenticationProvider customAuthenticationProvider, TokenAuthenticationService tokenAuthenticationService) {
        super(userService, customAuthenticationProvider, tokenAuthenticationService);
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) throws BadRequestException {
        validateRequest(loginRequest);
        LoginResponse loginResponse = handleAuthentication(loginRequest);
        return (ResponseEntity) ResponseEntity.ok(createSuccessResponse(loginResponse));
    }

    @DeleteMapping(value = "/logout")
    public ResponseEntity logout(@RequestHeader("X-Authorization") String token) throws BadRequestException {
        tokenAuthenticationService.removeAuthentication(token);
        return (ResponseEntity) ResponseEntity.status(HttpStatus.FORBIDDEN).body(createErrorResponse("Logged out"));
    }

    @GetMapping(value = "/user")
    public ResponseEntity getCurrentUser(@RequestHeader("X-Authorization") String token) throws BadRequestException {
        AuthenticationData authenticationData = tokenAuthenticationService.getAuthenticationData(token);
        //TODO: Remove after implementation of token persistance.
        if (authenticationData == null) {
            throw new BadRequestException("Your token is invalid because of server restart.");
        }
        LoginResponse loginResponse = authenticationDataToLoginResponse(authenticationData);
        return (ResponseEntity) ResponseEntity.ok(createSuccessResponse(loginResponse));
    }

    private LoginResponse handleAuthentication(LoginRequest loginRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password);
        authentication = authenticationProvider.authenticate(authentication);
        AuthenticationData authenticationData = tokenAuthenticationService.saveAuthentication(authentication, LocalDateTime.now());
        return authenticationDataToLoginResponse(authenticationData);
    }

    private void validateRequest(LoginRequest loginRequest) throws BadRequestException {
        if (!loginRequest.isValid()) {
            throw new BadRequestException("In order to login you need to provide username and password.");
        }
    }
}
