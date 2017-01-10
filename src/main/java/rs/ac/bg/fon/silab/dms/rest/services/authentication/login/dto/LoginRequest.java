package rs.ac.bg.fon.silab.dms.rest.services.authentication.login.dto;


import static rs.ac.bg.fon.silab.dms.util.StringUtils.isStringEmptyOrNull;

public class LoginRequest {
    public String username;
    public String password;

    public boolean isValid() {
        return !isStringEmptyOrNull(username)
                && !isStringEmptyOrNull(password);
    }


}
