package rs.ac.bg.fon.silab.dms.rest.services.users.dto;

import static org.springframework.util.StringUtils.isEmpty;
import static rs.ac.bg.fon.silab.dms.core.model.Role.ADMIN;
import static rs.ac.bg.fon.silab.dms.core.model.Role.USER;

public class UserRequest {
    public String username;
    public String password;
    public String role;

    public boolean isValid() {
        return !isEmpty(username) && !isEmpty(password) && !isEmpty(role)
                && (role.toUpperCase().equals(ADMIN.toString()) || role.toUpperCase().equals(USER.toString()));
    }
}
