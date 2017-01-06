package rs.ac.bg.fon.silab.dms.dataaccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.dms.dataaccess.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
