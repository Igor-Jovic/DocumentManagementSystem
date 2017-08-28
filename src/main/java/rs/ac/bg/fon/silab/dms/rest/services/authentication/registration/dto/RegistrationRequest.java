package rs.ac.bg.fon.silab.dms.rest.services.authentication.registration.dto;

import static org.springframework.util.StringUtils.isEmpty;

public class RegistrationRequest {
    public String username;
    public String password;
    public String companyName;
    public String companyDescription;

    public boolean isValid() {
        return !isEmpty(username)
                && !isEmpty(password)
                && !isEmpty(companyName);
    }
}