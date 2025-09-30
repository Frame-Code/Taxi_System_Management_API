package io.github.frame_code.domain.repository;

import io.github.frame_code.domain.entities.City;
import io.github.frame_code.domain.entities.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    @Query("SELECT p FROM Province p WHERE p.name = :name")
    Optional<Province> findProvinceByName(@Param("name") String name);

    @Query("SELECT c FROM City c WHERE c.province.name = :name")
    List<City> findCitiesByProvince(@Param("name") String name);


}
