package by.itechart.film2nightApp.repository;

import by.itechart.film2nightApp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
