package rs.ac.bg.fon.silab.dms.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.dms.core.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
