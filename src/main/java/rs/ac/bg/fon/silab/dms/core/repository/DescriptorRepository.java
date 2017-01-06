package rs.ac.bg.fon.silab.dms.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.dms.core.model.Descriptor;

public interface DescriptorRepository extends JpaRepository<Descriptor, Long> {
}
