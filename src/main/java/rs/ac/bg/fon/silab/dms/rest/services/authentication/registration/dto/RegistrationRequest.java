package rs.ac.bg.fon.silab.dms.rest.services.authentication.registration.dto;


import static rs.ac.bg.fon.silab.dms.util.StringUtils.isStringEmptyOrNull;

public class RegistrationRequest {
    public String username;
    public String password;
    public String companyName;

    public boolean isValid() {
        return !isStringEmptyOrNull(username)
                && !isStringEmptyOrNull(password)
                && !isStringEmptyOrNull(companyName);
    }
}
