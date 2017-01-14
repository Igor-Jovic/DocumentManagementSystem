package rs.ac.bg.fon.silab.dms.rest.services.authentication.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.core.model.Company;
import rs.ac.bg.fon.silab.dms.core.model.User;
import rs.ac.bg.fon.silab.dms.core.service.CompanyService;
import rs.ac.bg.fon.silab.dms.core.service.UserService;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.AuthenticationRestService;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.registration.dto.RegistrationRequest;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.registration.dto.RegistrationResponse;

import static rs.ac.bg.fon.silab.dms.rest.model.ApiResponse.createSuccessResponse;
import static rs.ac.bg.fon.silab.dms.rest.services.authentication.AuthenticationMapper.registrationRequestToUser;
import static rs.ac.bg.fon.silab.dms.rest.services.authentication.AuthenticationMapper.userToRegistrationResponse;

@RestController
public class RegistrationRestService extends AuthenticationRestService {


    @PostMapping(value = "/registration")
    public ResponseEntity register(@RequestBody RegistrationRequest registrationRequest) throws BadRequestException {
        validateRequest(registrationRequest);
        Company company = companyService.createCompany(new Company(registrationRequest.companyName));
        User user = userService.createUser(registrationRequestToUser(registrationRequest, company));
        return (ResponseEntity) ResponseEntity.ok(createSuccessResponse(userToRegistrationResponse(user)));
    }

    private void validateRequest(RegistrationRequest registrationRequest) throws BadRequestException {
        if (!registrationRequest.isValid()) {
            throw new BadRequestException("In order to register you need to provide company name, username and password.");
        }
    }


}
