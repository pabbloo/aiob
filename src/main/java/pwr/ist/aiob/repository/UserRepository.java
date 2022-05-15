package pwr.ist.aiob.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pwr.ist.aiob.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
