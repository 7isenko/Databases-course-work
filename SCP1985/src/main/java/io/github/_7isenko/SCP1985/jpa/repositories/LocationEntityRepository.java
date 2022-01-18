package io.github._7isenko.SCP1985.jpa.repositories;

import io.github._7isenko.SCP1985.jpa.entities.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.stereotype.Repository;

import javax.persistence.Basic;
import javax.persistence.FetchType;

@Repository
public interface LocationEntityRepository extends JpaRepository<LocationEntity, Integer> {
}