package io.github.frame_code.domain.repository;

import io.github.frame_code.domain.entities.Fare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IFareRepository extends JpaRepository<Fare, Integer> {
    @Query("SELECT f FROM Fare f WHERE f.id = 1")
    Optional<Fare> find();

}
