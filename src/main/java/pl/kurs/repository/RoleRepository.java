package pl.kurs.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.entity.model.ERole;
import pl.kurs.entity.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

}
