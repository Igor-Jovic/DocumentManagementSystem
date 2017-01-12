package rs.ac.bg.fon.silab.dms.core.service;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.core.model.Company;
import rs.ac.bg.fon.silab.dms.core.model.Role;
import rs.ac.bg.fon.silab.dms.core.model.User;
import rs.ac.bg.fon.silab.dms.core.repository.UserRepository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Test(expected = BadRequestException.class)
    public void createUser_userExists_throwBadRequestException() throws BadRequestException {
        User user = new User("admin", "admin", Role.ADMIN, new Company("adminCompany"));
        UserRepository userRepositoryMock = mock(UserRepository.class);
        when(userRepositoryMock.findByUsername("admin")).thenReturn(user);
        UserService testee = new UserService(userRepositoryMock, null);

        testee.createUser(user);
    }

    @Test(expected = IllegalStateException.class)
    public void createUser_userInIllegalState_throwIllegalStateException() throws BadRequestException {
        User user = new User("admin", null, Role.ADMIN, null);
        UserRepository userRepositoryMock = mock(UserRepository.class);
        when(userRepositoryMock.findByUsername("admin")).thenReturn(null);
        UserService testee = new UserService(userRepositoryMock, null);

        testee.createUser(user);
    }

    @Test
    public void createUser_validUser_saveUser() throws BadRequestException {
        //GIVEN
        User user = new User("admin", "admin", Role.ADMIN, new Company("admin"));
        UserRepository userRepositoryMock = mock(UserRepository.class);
        BCryptPasswordEncoder encoderMock = mock(BCryptPasswordEncoder.class);
        when(userRepositoryMock.findByUsername("admin")).thenReturn(null);
        when(encoderMock.encode("admin")).thenReturn("adminPassEncoded");
        UserService testee = new UserService(userRepositoryMock, encoderMock);
        //WHEN
        testee.createUser(user);
        //THEN
        verify(encoderMock, times(1)).encode("admin");
        verify(userRepositoryMock, times(1)).saveAndFlush(user);
    }
}