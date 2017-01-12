package rs.ac.bg.fon.silab.dms.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.core.model.Company;
import rs.ac.bg.fon.silab.dms.core.repository.CompanyRepository;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public void setCompanyRepository(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company createCompany(Company company) throws BadRequestException {
        if (companyRepository.findByName(company.getName()) != null) {
            throw new BadRequestException("Company with given name already exists.");
        }
        return companyRepository.saveAndFlush(company);
    }

    public Company getProcess(Long id) throws BadRequestException {
        Company company = companyRepository.findOne(id);
        if (company == null) {
            throw new BadRequestException("Company does not exists.");
        }
        return company;
    }
}
