package io.github.frame_code.domain.repository;

import Enums.entitiesEnums.ROLE_NAME;
import io.github.frame_code.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Long, Role> {

    Optional<Role> findByRoleName(ROLE_NAME roleName);
}
