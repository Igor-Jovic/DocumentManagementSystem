package rs.ac.bg.fon.silab.dms.core.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.dms.core.exception.DMSErrorException;
import rs.ac.bg.fon.silab.dms.core.model.Company;
import rs.ac.bg.fon.silab.dms.core.model.CompanyES;
import rs.ac.bg.fon.silab.dms.core.repository.CompanyRepository;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    CompanyService(CompanyRepository companyRepository, ElasticsearchClient elasticsearchClient) {
        this.companyRepository = companyRepository;
        this.elasticsearchClient = elasticsearchClient;
    }

    public Company createCompany(Company company) throws DMSErrorException {
        if (companyRepository.findByName(company.getName()) != null) {
            throw new DMSErrorException("Company with given name already exists.");
        }
        company = companyRepository.saveAndFlush(company);
        elasticsearchClient.save(new CompanyES(company));
        return company;
    }

    public List<CompanyES> getByNameLike(String name, Pageable pageable) {
        return elasticsearchClient.findCompaniesByNameLike(name, pageable);
    }

    public void updateCompany(Company company) {
        companyRepository.save(company);
        elasticsearchClient.save(new CompanyES(company));
    }

    public List<CompanyES> getByNameOrDescription(String searchExpression, Pageable pageable) {
        return elasticsearchClient.findComapniesByNameOrDescription(searchExpression, pageable);
    }

}
