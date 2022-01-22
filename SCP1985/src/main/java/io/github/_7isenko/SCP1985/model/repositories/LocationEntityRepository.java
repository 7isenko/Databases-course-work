package io.github._7isenko.SCP1985.model.repositories;

import io.github._7isenko.SCP1985.model.entities.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationEntityRepository extends JpaRepository<LocationEntity, Integer> {
    List<LocationEntity> findByIdGreaterThanAndIdLessThan(int id, int id2);
}