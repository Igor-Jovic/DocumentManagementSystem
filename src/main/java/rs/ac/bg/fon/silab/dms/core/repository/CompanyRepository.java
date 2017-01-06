package rs.ac.bg.fon.silab.dms.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.dms.core.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByName(String name);
}
