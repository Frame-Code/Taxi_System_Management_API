package io.github.frame_code.domain.repository;

import io.github.frame_code.domain.entities.City;
import io.github.frame_code.domain.entities.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query("SELECT c FROM City c WHERE c.province = :province")
    List<City> findCitiesByProvince(@Param("province") Province province);
}
