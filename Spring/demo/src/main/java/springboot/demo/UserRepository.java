package springboot.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUserName(String username);
    Optional<User> findById(Long id);
}
