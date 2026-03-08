package io.github.frame_code.domain.repository;

import Enums.entitiesEnums.ROLE_NAME;
import io.github.frame_code.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(ROLE_NAME roleName);
}
