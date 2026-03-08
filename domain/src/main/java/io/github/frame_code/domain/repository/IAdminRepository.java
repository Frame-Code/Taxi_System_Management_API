package io.github.frame_code.domain.repository;

import io.github.frame_code.domain.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdminRepository extends JpaRepository<Admin, Long> {
}
