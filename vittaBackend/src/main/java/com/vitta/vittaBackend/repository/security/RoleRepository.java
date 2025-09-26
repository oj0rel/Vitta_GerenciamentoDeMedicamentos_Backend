package com.vitta.vittaBackend.repository.security;

import com.vitta.vittaBackend.entity.Role;
import com.vitta.vittaBackend.enums.security.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
