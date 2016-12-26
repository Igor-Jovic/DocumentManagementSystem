package rs.ac.bg.fon.silab.dms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.dms.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
