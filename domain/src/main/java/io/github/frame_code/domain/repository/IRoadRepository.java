package io.github.frame_code.domain.repository;

import io.github.frame_code.domain.entities.Road;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoadRepository extends JpaRepository<Road, Long> {
}
