package rs.ac.bg.fon.silab.dms.rest.services.users;

import rs.ac.bg.fon.silab.dms.core.model.Company;
import rs.ac.bg.fon.silab.dms.core.model.Role;
import rs.ac.bg.fon.silab.dms.core.model.User;
import rs.ac.bg.fon.silab.dms.rest.services.users.dto.UserRequest;
import rs.ac.bg.fon.silab.dms.rest.services.users.dto.UserResponse;

class UsersMapper {

    static User userRequestToUser(UserRequest userRequest, Company company) {
        User user = new User(userRequest.username, userRequest.password, Role.valueOf(userRequest.role.toUpperCase()), company);
        return user;
    }

    static UserResponse userToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.id = user.getId();
        userResponse.role = user.getRole().toString();
        userResponse.username = user.getUsername();
        return userResponse;
    }
}
