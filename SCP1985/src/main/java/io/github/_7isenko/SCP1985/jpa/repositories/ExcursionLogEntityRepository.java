package io.github._7isenko.SCP1985.jpa.repositories;

import io.github._7isenko.SCP1985.jpa.entities.ExcursionLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcursionLogEntityRepository extends JpaRepository<ExcursionLogEntity, Integer> {
    @Procedure(value = "go_on_excursion")
    int goOnExcursion(Integer scpObjectId, Integer personnelId, Integer equipmentId);
}