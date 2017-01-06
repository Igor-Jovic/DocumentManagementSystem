package rs.ac.bg.fon.silab.dms.dataaccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.dms.dataaccess.model.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
