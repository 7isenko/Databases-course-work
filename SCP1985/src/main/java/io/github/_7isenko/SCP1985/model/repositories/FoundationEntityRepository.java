package io.github._7isenko.SCP1985.model.repositories;

import io.github._7isenko.SCP1985.model.entities.FoundationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FoundationEntityRepository extends JpaRepository<FoundationEntity, Integer> {
    @Query("select count(f) from FoundationEntity f")
    Integer countAll();
}