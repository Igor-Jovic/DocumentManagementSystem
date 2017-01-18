package rs.ac.bg.fon.silab.dms.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.dms.core.model.CompanyProcess;

public interface ProcessRepository extends JpaRepository<CompanyProcess, Long> {

    List<CompanyProcess> findByParentProcessAndCompanyId(Long parentProcess, Long company);

}
