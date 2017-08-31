package rs.ac.bg.fon.silab.dms.core.service;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import rs.ac.bg.fon.silab.dms.core.exception.DMSErrorException;
import rs.ac.bg.fon.silab.dms.core.model.Company;
import rs.ac.bg.fon.silab.dms.core.model.Role;
import rs.ac.bg.fon.silab.dms.core.model.User;
import rs.ac.bg.fon.silab.dms.core.repository.UserRepository;
import static org.mockito.Mockito.*;
import rs.ac.bg.fon.silab.dms.core.model.UserES;

public class UserServiceTest {

    @Test(expected = DMSErrorException.class)
    public void validateUser_userExists_throwBadRequestException() throws DMSErrorException {
        Company adminCompany = new Company("adminCompany");
        User user = new User("admin", "admin", Role.ADMIN, adminCompany);
        UserRepository userRepositoryMock = mock(UserRepository.class);
        CompanyService companyServiceMock = mock(CompanyService.class);

        when(userRepositoryMock.findByUsername("admin")).thenReturn(user);
        when(companyServiceMock.createCompany(adminCompany)).thenReturn(adminCompany);

        UserService testee = new UserService(userRepositoryMock, null, companyServiceMock, null);

        testee.validateUser(user);
    }

    @Test(expected = IllegalStateException.class)
    public void validateUser_userInIllegalState_throwIllegalStateException() throws DMSErrorException {
        User user = new User("admin", null, Role.ADMIN, null);
        UserRepository userRepositoryMock = mock(UserRepository.class);

        when(userRepositoryMock.findByUsername("admin")).thenReturn(null);

        UserService testee = new UserService(userRepositoryMock, null, null, null);
        testee.validateUser(user);
    }

    @Test
    public void createUserWithNewCompany_validUser_saveUser() throws DMSErrorException {
        Company adminCompany = new Company("admin");
        User user = new User("admin", "admin", Role.ADMIN, adminCompany);
        UserRepository userRepositoryMock = mock(UserRepository.class);
        CompanyService companyServiceMock = mock(CompanyService.class);
        BCryptPasswordEncoder encoderMock = mock(BCryptPasswordEncoder.class);

        when(userRepositoryMock.findByUsername("admin")).thenReturn(null);
        when(encoderMock.encode("admin")).thenReturn("adminPassEncoded");
        when(companyServiceMock.createCompany(adminCompany)).thenReturn(adminCompany);

        UserService testee = new UserService(userRepositoryMock, encoderMock, companyServiceMock, null);
        testee.createUserWithNewCompany(user);

        verify(encoderMock, times(1)).encode("admin");
        verify(userRepositoryMock, times(1)).saveAndFlush(user);
        verify(companyServiceMock, times(1)).createCompany(adminCompany);
    }

    @Test(expected = DMSErrorException.class)
    public void createUserWithNewCompany_companyExists_doNotSaveUser() throws DMSErrorException {
        Company adminCompany = new Company("admin");
        User user = new User("admin", "admin", Role.ADMIN, adminCompany);
        UserRepository userRepositoryMock = mock(UserRepository.class);
        CompanyService companyServiceMock = mock(CompanyService.class);

        when(userRepositoryMock.findByUsername("admin")).thenReturn(null);
        when(companyServiceMock.createCompany(adminCompany)).thenThrow(new DMSErrorException("Company with given name already exists."));

        UserService testee = new UserService(userRepositoryMock, null, companyServiceMock, null);
        testee.createUserWithNewCompany(user);

        verify(userRepositoryMock, never()).saveAndFlush(user);
    }

    @Test
    public void createUser_userDoesntExist_saveUser() throws DMSErrorException {
        Company adminCompany = new Company("userCompany");
        User user = new User("user", "user", Role.USER, adminCompany);
        UserRepository userRepositoryMock = mock(UserRepository.class);
        CompanyService companyServiceMock = mock(CompanyService.class);
        BCryptPasswordEncoder encoderMock = mock(BCryptPasswordEncoder.class);
        ElasticsearchClient elasticsearchClient = mock(ElasticsearchClient.class);

        when(userRepositoryMock.findByUsername("user")).thenReturn(null);
        when(companyServiceMock.createCompany(adminCompany)).thenReturn(adminCompany);
        when(encoderMock.encode("admin")).thenReturn("adminPassEncoded");
        doNothing().when(elasticsearchClient).save(any(UserES.class));

        UserService testee = new UserService(userRepositoryMock, encoderMock, companyServiceMock, elasticsearchClient);
        testee.createUser(user);

        verify(userRepositoryMock, times(1)).saveAndFlush(user);
    }
}