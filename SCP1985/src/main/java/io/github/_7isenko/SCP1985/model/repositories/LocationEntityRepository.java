package io.github._7isenko.SCP1985.model.repositories;

import io.github._7isenko.SCP1985.model.entities.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationEntityRepository extends JpaRepository<LocationEntity, Integer> {
    @Query(nativeQuery = true, value = "select * from location LIMIT ?1 offset (select count(*) from location)-?1")
    List<LocationEntity> findByIdGreaterThanAndIdLessThan(int amount);
}