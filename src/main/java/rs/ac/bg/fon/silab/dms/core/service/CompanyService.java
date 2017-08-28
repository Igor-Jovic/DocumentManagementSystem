package rs.ac.bg.fon.silab.dms.core.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.dms.core.exception.DMSErrorException;
import rs.ac.bg.fon.silab.dms.core.model.Company;
import rs.ac.bg.fon.silab.dms.core.model.CompanyES;
import rs.ac.bg.fon.silab.dms.core.repository.CompanyRepository;
import rs.ac.bg.fon.silab.dms.core.repository.es.CompanyESRepository;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyESRepository companyESRepository;

    CompanyService(CompanyRepository companyRepository, CompanyESRepository companyESRepository) {
        this.companyRepository = companyRepository;
        this.companyESRepository = companyESRepository;
    }

    public Company createCompany(Company company) throws DMSErrorException {
        if (companyRepository.findByName(company.getName()) != null) {
            throw new DMSErrorException("Company with given name already exists.");
        }
        company = companyRepository.saveAndFlush(company);
        companyESRepository.save(new CompanyES(company));
        return company;
    }

    public List<CompanyES> getByNameLike(String name, Pageable pageable) {
        return companyESRepository.findByNameLike(name, pageable).getContent();
    }

}
