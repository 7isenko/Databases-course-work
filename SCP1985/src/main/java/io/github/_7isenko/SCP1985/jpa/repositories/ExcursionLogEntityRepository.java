package io.github._7isenko.SCP1985.jpa.repositories;

import io.github._7isenko.SCP1985.jpa.entities.ExcursionLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExcursionLogEntityRepository extends JpaRepository<ExcursionLogEntity, Integer> {
//    @Query(nativeQuery = true, value = "select go_on_excursion(?1,?2,?3)")
//    void goOnExcursion(Integer scpObjectId, Integer personnelId, Integer equipmentId);

    @Procedure(value = "go_on_excursion")
    void goOnExcursion(Integer scpObjectId, Integer personnelId, Integer equipmentId);
}