package rs.ac.bg.fon.silab.dms.rest.services.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.dms.core.exception.DMSErrorException;
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
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    public UserController(UserService userService, TokenAuthenticationService tokenAuthenticationService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity getAllEmployees(@RequestHeader("X-Authorization") String token) throws DMSErrorException {
        User authenticatedUser = tokenAuthenticationService.getAuthenticatedUser(token);
        List<User> users = authenticatedUser.getCompany().getEmployees();
        List<UserResponse> userResponseList = users.stream()
                .map(UsersMapper::userToUserResponse)
                .collect(toList());
        return ResponseEntity.ok(createSuccessResponse(userResponseList));
    }

    @PostMapping
    public ResponseEntity createUser(@RequestHeader("X-Authorization") String token, @RequestBody UserRequest userRequest) throws DMSErrorException {
        validateRequest(userRequest);
        User authenticatedUser = tokenAuthenticationService.getAuthenticatedUser(token);
        User newUser = userRequestToUser(userRequest, authenticatedUser.getCompany());
        newUser = userService.createUser(newUser);
        return ResponseEntity.ok(createSuccessResponse(userToUserResponse(newUser)));
    }

    private void validateRequest(UserRequest userRequest) throws DMSErrorException {
        if (!userRequest.isValid()) {
            throw new DMSErrorException("In order to register you need to provide valid role, username and password.");
        }
    }
}
