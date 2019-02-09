package f1ni6x.hu.elte.HealthApplication.repository;

import f1ni6x.hu.elte.HealthApplication.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
}