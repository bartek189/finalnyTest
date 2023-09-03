package pl.kurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kurs.entity.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.userName = :username")
    Optional<User> findByUserNameWithRoles(String username);

    @Query("select u from User u left join fetch u.createdShape where u.userName =?1")
    Optional<User> findByIdWithShapes(String name);


}
