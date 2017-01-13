package rs.ac.bg.fon.silab.dms.rest.services.authentication;


import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.core.model.Company;
import rs.ac.bg.fon.silab.dms.core.model.Role;
import rs.ac.bg.fon.silab.dms.core.model.User;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.login.dto.LoginResponse;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.registration.dto.RegistrationRequest;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.registration.dto.RegistrationResponse;
import rs.ac.bg.fon.silab.dms.security.AuthenticationData;

public class AuthenticationMapper {

    public static User registrationRequestToUser(RegistrationRequest registrationRequest) throws BadRequestException {
        Company company = new Company(registrationRequest.companyName);
        return new User(registrationRequest.username, registrationRequest.password, Role.ADMIN, company);
    }

    public static RegistrationResponse userToRegistrationResponse(User user) {
        return new RegistrationResponse(user.getUsername(), user.getRole().toString(), user.getCompany().getName());
    }

    public static LoginResponse authenticationDataToLoginResponse(AuthenticationData authenticationData) {
        String username = (String) authenticationData.getAuthentication().getPrincipal();
        return new LoginResponse(username, authenticationData.getRole(), authenticationData.getToken());
    }

}
