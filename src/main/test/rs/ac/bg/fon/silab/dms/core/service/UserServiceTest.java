package rs.ac.bg.fon.silab.dms.core.service;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.core.model.Company;
import rs.ac.bg.fon.silab.dms.core.model.Role;
import rs.ac.bg.fon.silab.dms.core.model.User;
import rs.ac.bg.fon.silab.dms.core.repository.UserRepository;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Test(expected = BadRequestException.class)
    public void createAdmin_userExists_throwBadRequestException() throws BadRequestException {

        Company adminCompany = new Company("adminCompany");
        User user = new User("admin", "admin", Role.ADMIN, adminCompany);
        UserRepository userRepositoryMock = mock(UserRepository.class);
        CompanyService companyServiceMock = mock(CompanyService.class);

        when(userRepositoryMock.findByUsername("admin")).thenReturn(user);
        when(companyServiceMock.createCompany(adminCompany)).thenReturn(adminCompany);

        UserService testee = new UserService(userRepositoryMock, null, companyServiceMock);

        testee.createAdmin(user);
    }

    @Test(expected = IllegalStateException.class)
    public void createAdmin_userInIllegalState_throwIllegalStateException() throws BadRequestException {
        User user = new User("admin", null, Role.ADMIN, null);
        UserRepository userRepositoryMock = mock(UserRepository.class);
        when(userRepositoryMock.findByUsername("admin")).thenReturn(null);
        UserService testee = new UserService(userRepositoryMock, null, null);

        testee.createAdmin(user);
    }

    @Test
    public void createAdmin_validUser_saveUser() throws BadRequestException {
        //GIVEN
        Company adminCompany = new Company("admin");
        User user = new User("admin", "admin", Role.ADMIN, adminCompany);
        UserRepository userRepositoryMock = mock(UserRepository.class);
        CompanyService companyServiceMock = mock(CompanyService.class);
        BCryptPasswordEncoder encoderMock = mock(BCryptPasswordEncoder.class);

        when(userRepositoryMock.findByUsername("admin")).thenReturn(null);
        when(encoderMock.encode("admin")).thenReturn("adminPassEncoded");
        when(companyServiceMock.createCompany(adminCompany)).thenReturn(adminCompany);

        UserService testee = new UserService(userRepositoryMock, encoderMock, companyServiceMock);
        //WHEN
        testee.createAdmin(user);
        //THEN
        verify(encoderMock, times(1)).encode("admin");
        verify(userRepositoryMock, times(1)).saveAndFlush(user);
    }

    @Test(expected = BadRequestException.class)
    public void createAdmin_companyExists_doNotSaveUser() throws BadRequestException {
        //GIVEN
        Company adminCompany = new Company("admin");
        User user = new User("admin", "admin", Role.ADMIN, adminCompany);
        UserRepository userRepositoryMock = mock(UserRepository.class);
        CompanyService companyServiceMock = mock(CompanyService.class);
        when(userRepositoryMock.findByUsername("admin")).thenReturn(null);
        when(companyServiceMock.createCompany(adminCompany)).thenThrow(new BadRequestException("Company with given name already exists."));

        UserService testee = new UserService(userRepositoryMock, null, companyServiceMock);
        //WHEN
        testee.createAdmin(user);
        //THEN
        verify(userRepositoryMock, never()).saveAndFlush(user);
    }
}