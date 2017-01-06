package rs.ac.bg.fon.silab.dms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.dms.model.Activity;
import rs.ac.bg.fon.silab.dms.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
