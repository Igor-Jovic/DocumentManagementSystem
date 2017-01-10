package rs.ac.bg.fon.silab.dms.rest.services.authentication.registration;


import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.core.model.Company;
import rs.ac.bg.fon.silab.dms.core.model.Role;
import rs.ac.bg.fon.silab.dms.core.model.User;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.registration.dto.RegistrationRequest;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.registration.dto.RegistrationResponse;

class RegistrationMapper {

    static User mapRequestToUser(RegistrationRequest registrationRequest) throws BadRequestException {
        Company company = new Company(registrationRequest.companyName);
        return new User(registrationRequest.username, registrationRequest.password, Role.ADMIN, company);
    }

    static RegistrationResponse mapUserToResponse(User user) {
        return new RegistrationResponse(user.getUsername(), user.getRole().toString(), user.getCompany().getName());
    }
}
