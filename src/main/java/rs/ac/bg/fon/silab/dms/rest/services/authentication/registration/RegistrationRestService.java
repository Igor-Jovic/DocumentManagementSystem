package rs.ac.bg.fon.silab.dms.rest.services.authentication.registration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.silab.dms.core.exception.DMSErrorException;
import rs.ac.bg.fon.silab.dms.core.model.User;
import rs.ac.bg.fon.silab.dms.core.service.UserService;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.AuthenticationRestService;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.registration.dto.RegistrationRequest;
import rs.ac.bg.fon.silab.dms.security.CustomAuthenticationProvider;
import rs.ac.bg.fon.silab.dms.security.TokenAuthenticationService;

import static rs.ac.bg.fon.silab.dms.rest.model.ApiResponse.createSuccessResponse;
import static rs.ac.bg.fon.silab.dms.rest.services.authentication.AuthenticationMapper.registrationRequestToUser;
import static rs.ac.bg.fon.silab.dms.rest.services.authentication.AuthenticationMapper.userToRegistrationResponse;

@RestController
public class RegistrationRestService extends AuthenticationRestService {

    public RegistrationRestService(UserService userService, CustomAuthenticationProvider customAuthenticationProvider, TokenAuthenticationService tokenAuthenticationService) {
        super(userService, customAuthenticationProvider, tokenAuthenticationService);
    }

    @PostMapping(value = "/registration")
    public ResponseEntity register(@RequestBody RegistrationRequest registrationRequest) throws DMSErrorException {
        validateRequest(registrationRequest);
        User user = userService.createUserWithNewCompany(registrationRequestToUser(registrationRequest));
        return (ResponseEntity) ResponseEntity.ok(createSuccessResponse(userToRegistrationResponse(user)));
    }

    private void validateRequest(RegistrationRequest registrationRequest) throws DMSErrorException {
        if (!registrationRequest.isValid()) {
            throw new DMSErrorException("In order to register you need to provide company name, username and password.");
        }
    }


}
