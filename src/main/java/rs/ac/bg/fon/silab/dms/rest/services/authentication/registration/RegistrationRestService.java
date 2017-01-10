package rs.ac.bg.fon.silab.dms.rest.services.authentication.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.core.model.User;
import rs.ac.bg.fon.silab.dms.core.service.UserService;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.AuthenticationRestService;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.registration.dto.RegistrationRequest;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.registration.dto.RegistrationResponse;

import static rs.ac.bg.fon.silab.dms.rest.services.authentication.registration.RegistrationMapper.mapUserToResponse;
import static rs.ac.bg.fon.silab.dms.rest.services.authentication.registration.RegistrationMapper.mapRequestToUser;

@RestController
public class RegistrationRestService extends AuthenticationRestService {

    @Autowired
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest) throws BadRequestException {
        validateRequest(registrationRequest);
        User user = userService.createUser(mapRequestToUser(registrationRequest));
        return ResponseEntity.ok(mapUserToResponse(user));
    }

    private void validateRequest(RegistrationRequest registrationRequest) throws BadRequestException {
        if (!registrationRequest.isValid()) {
            throw new BadRequestException("A problem occured. In order to register you need to provide company name, username and password.");
        }
    }


}
