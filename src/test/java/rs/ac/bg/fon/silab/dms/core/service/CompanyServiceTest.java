package rs.ac.bg.fon.silab.dms.core.service;

import org.junit.Test;
import rs.ac.bg.fon.silab.dms.core.exception.DMSErrorException;
import rs.ac.bg.fon.silab.dms.core.model.Company;
import rs.ac.bg.fon.silab.dms.core.repository.CompanyRepository;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CompanyServiceTest {

    @Test(expected = DMSErrorException.class)
    public void createCompany_companyExists_throwException() throws DMSErrorException {
        Company company = new Company("Company");
        CompanyRepository companyRepositoryMock = mock(CompanyRepository.class);
        when(companyRepositoryMock.findByName(any())).thenReturn(company);

        CompanyService testee = new CompanyService(companyRepositoryMock);

        testee.createCompany(company);
    }

    @Test
    public void createCompany_companyDoesntExists_createsCompany() throws DMSErrorException {
        Company company = new Company("Company");
        CompanyRepository companyRepositoryMock = mock(CompanyRepository.class);
        when(companyRepositoryMock.findByName(any())).thenReturn(null);
        when(companyRepositoryMock.saveAndFlush(any())).thenReturn(company);

        CompanyService testee = new CompanyService(companyRepositoryMock);

        Company newCompany = testee.createCompany(company);

        assertEquals(company.getName(), newCompany.getName());
    }

}