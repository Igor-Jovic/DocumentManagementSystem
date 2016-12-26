package rs.ac.bg.fon.silab.dms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.dms.model.Activity;
import rs.ac.bg.fon.silab.dms.model.Descriptor;

public interface DescriptorRepository extends JpaRepository<Descriptor, Long> {
}
