package io.github.frame_code.domain.repository;

import io.github.frame_code.domain.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDriverRepository extends JpaRepository<Driver, Long> {
}
