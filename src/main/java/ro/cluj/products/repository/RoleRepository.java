package ro.cluj.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cluj.products.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}