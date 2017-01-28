package rs.ac.bg.fon.silab.dms.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.dms.core.model.DocumentType;

public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {
    
    List<DocumentType> getAllByCompanyId(Long companyId);
    
    DocumentType getByNameAndCompanyId(String name, Long companyId);
    
}
