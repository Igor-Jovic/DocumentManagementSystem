package rs.ac.bg.fon.silab.dms.core.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.dms.core.exception.DMSException;
import rs.ac.bg.fon.silab.dms.core.model.Company;
import rs.ac.bg.fon.silab.dms.core.repository.CompanyRepository;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    
    public void setCompanyRepository(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company createCompany(Company company) throws DMSException {
        if (companyRepository.findByName(company.getName()) != null) {
            throw new DMSException("Company with given name already exists.");
        }
        return companyRepository.saveAndFlush(company);
    }
}
