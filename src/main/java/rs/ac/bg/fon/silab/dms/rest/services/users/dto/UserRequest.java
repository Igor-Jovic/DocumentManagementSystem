package rs.ac.bg.fon.silab.dms.rest.services.users.dto;

import rs.ac.bg.fon.silab.dms.core.model.Role;

import static rs.ac.bg.fon.silab.dms.core.model.Role.*;
import static rs.ac.bg.fon.silab.dms.util.StringUtils.isStringEmptyOrNull;

/**
 * Created by igor on 1/14/17.
 */
public class UserRequest {
    public String username;
    public String password;
    public String role;

    public boolean isValid() {
        return !isStringEmptyOrNull(username)
                && !isStringEmptyOrNull(password)
                && !isStringEmptyOrNull(role)
                && (role.toUpperCase().equals(ADMIN.toString()) || role.toUpperCase().equals(USER.toString()));
    }
}
