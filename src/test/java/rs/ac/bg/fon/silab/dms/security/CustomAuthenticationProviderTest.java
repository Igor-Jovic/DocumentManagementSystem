package rs.ac.bg.fon.silab.dms.security;

import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import rs.ac.bg.fon.silab.dms.core.model.Company;
import rs.ac.bg.fon.silab.dms.core.model.Role;
import rs.ac.bg.fon.silab.dms.core.model.User;
import rs.ac.bg.fon.silab.dms.core.service.UserService;
import rs.ac.bg.fon.silab.dms.security.exception.UnknownUserException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CustomAuthenticationProviderTest {

    @Test(expected = UnknownUserException.class)
    public void authenticate_userDoesntExists_throwUnknownUserException() {
        UserService userServiceMock = mock(UserService.class);
        UsernamePasswordAuthenticationToken authentication = createAuthentication();
        when(userServiceMock.getUser((String) authentication.getPrincipal())).thenReturn(null);

        CustomAuthenticationProvider testee = new CustomAuthenticationProvider(userServiceMock, null);

        testee.authenticate(authentication);
    }

    @Test(expected = UnknownUserException.class)
    public void authenticate_invalidPass_throwUnknownUserException() {
        UsernamePasswordAuthenticationToken authentication = createAuthentication();
        User user = new User("admin", "admin", Role.ADMIN, new Company("admin"));
        UserService userServiceMock = mock(UserService.class);
        BCryptPasswordEncoder encoderMock = mock(BCryptPasswordEncoder.class);
        when(userServiceMock.getUser((String) authentication.getPrincipal())).thenReturn(user);
        when(encoderMock.matches(any(), any())).thenThrow(new UnknownUserException(""));

        CustomAuthenticationProvider testee = new CustomAuthenticationProvider(userServiceMock, encoderMock);

        testee.authenticate(authentication);
    }

    @Test
    public void authenticate_validPassword_returnNewAuthentication() {
        UsernamePasswordAuthenticationToken authentication = createAuthentication();
        User user = new User("admin", "admin", Role.ADMIN, new Company("admin"));
        UserService userServiceMock = mock(UserService.class);
        BCryptPasswordEncoder encoderMock = mock(BCryptPasswordEncoder.class);
        when(userServiceMock.getUser((String) authentication.getPrincipal())).thenReturn(user);
        when(encoderMock.matches(any(), any())).thenReturn(true);

        CustomAuthenticationProvider testee = new CustomAuthenticationProvider(userServiceMock, encoderMock);

        Authentication authenticationAfterSuccess = testee.authenticate(authentication);
        assertEquals(user.getUsername(), authenticationAfterSuccess.getPrincipal());
        assertEquals(user.getPassword(), authenticationAfterSuccess.getCredentials());
        assertEquals(1, authenticationAfterSuccess.getAuthorities().size());

    }


    private UsernamePasswordAuthenticationToken createAuthentication() {
        return new UsernamePasswordAuthenticationToken("admin", "pass",
                AuthorityUtils.createAuthorityList("ADMIN"));
    }
}