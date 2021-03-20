package pl.movie.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.movie.registration.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
