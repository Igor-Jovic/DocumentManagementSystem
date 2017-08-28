package rs.ac.bg.fon.silab.dms.core.service;

import java.util.List;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
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

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

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

    public void updateCompany(Company company) {
        companyRepository.save(company);
        companyESRepository.save(new CompanyES(company));
    }

    public List<CompanyES> getByNameOrDescription(String searchExpression, Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(new BoolQueryBuilder().should(QueryBuilders.matchPhrasePrefixQuery("name", searchExpression))
                        .should(QueryBuilders.matchQuery("description", searchExpression).fuzziness(Fuzziness.TWO)))
                .withPageable(pageable)
                .withIndices("dms")
                .withTypes("company")
                .withSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .build();
        return elasticsearchTemplate.queryForPage(searchQuery, CompanyES.class).getContent();
    }

}
