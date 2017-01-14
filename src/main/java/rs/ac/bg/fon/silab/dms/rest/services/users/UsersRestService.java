package rs.ac.bg.fon.silab.dms.rest.services.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.core.model.Company;
import rs.ac.bg.fon.silab.dms.core.model.User;
import rs.ac.bg.fon.silab.dms.core.service.UserService;
import rs.ac.bg.fon.silab.dms.rest.services.users.dto.UserRequest;
import rs.ac.bg.fon.silab.dms.rest.services.users.dto.UserResponse;
import rs.ac.bg.fon.silab.dms.security.TokenAuthenticationService;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static rs.ac.bg.fon.silab.dms.rest.model.ApiResponse.createSuccessResponse;
import static rs.ac.bg.fon.silab.dms.rest.services.users.UsersMapper.userRequestToUser;
import static rs.ac.bg.fon.silab.dms.rest.services.users.UsersMapper.userToUserResponse;

@RestController
@RequestMapping("/users")
public class UsersRestService {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    public UsersRestService(UserService userService, TokenAuthenticationService tokenAuthenticationService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity getAllEmployees(@RequestHeader("X-Authorization") String token) throws BadRequestException {
        User authenticatedUser = getAuthenticatedUser(token);
        List<User> users = authenticatedUser.getCompany().getEmployees();
        List<UserResponse> userResponseList = users.stream()
                .map(UsersMapper::userToUserResponse)
                .collect(toList());
        return ResponseEntity.ok(createSuccessResponse(userResponseList));
    }

    @PostMapping
    public ResponseEntity createUser(@RequestHeader("X-Authorization") String token, @RequestBody UserRequest userRequest) throws BadRequestException {
        validateRequest(userRequest);
        User authenticatedUser = getAuthenticatedUser(token);
        User newUser = userRequestToUser(userRequest, authenticatedUser.getCompany());
        newUser = userService.createUser(newUser);
        return ResponseEntity.ok(createSuccessResponse(userToUserResponse(newUser)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity createUser(@RequestHeader("X-Authorization") String token, @PathVariable long id) throws BadRequestException {
        User authenticatedUser = getAuthenticatedUser(token);
        if (authenticatedUser.getId() == id) {
            throw new BadRequestException("You cannot delete your own account.");
        }
        User existingUser = getUserFromCompany(authenticatedUser.getCompany(), id);
        userService.deleteUser(existingUser);
        return ResponseEntity.ok(createSuccessResponse(userToUserResponse(existingUser)));
    }

    private User getUserFromCompany(Company company, long id) throws BadRequestException {
        List<User> users = company.getEmployees();
        List<User> existingUserList = users.stream()
                .filter(user -> user.getId() == id)
                .collect(toList());
        if (existingUserList.isEmpty()) {
            throw new BadRequestException("The user doesn't exist.");
        }
        return existingUserList.get(0);
    }


    private User getAuthenticatedUser(String token) {
        String authenticatedUserName = tokenAuthenticationService.getAuthenticationData(token)
                .getAuthentication().getName();
        User authenticatedUser = userService.getUser(authenticatedUserName);
        return authenticatedUser;
    }

    private void validateRequest(UserRequest userRequest) throws BadRequestException {
        if (!userRequest.isValid()) {
            throw new BadRequestException("In order to register you need to provide valid role, username and password.");
        }
    }
}
