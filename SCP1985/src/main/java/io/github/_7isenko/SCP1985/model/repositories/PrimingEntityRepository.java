package io.github._7isenko.SCP1985.model.repositories;

import io.github._7isenko.SCP1985.model.entities.PersonnelEntity;
import io.github._7isenko.SCP1985.model.entities.PrimingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PrimingEntityRepository extends JpaRepository<PrimingEntity, Integer> {
    @Transactional
    @Modifying
    @Query(value = "insert into priming (scp_object_id, personnel_id)" +
            " VALUES (:scp_id, :personnel_id)", nativeQuery = true)
    void executePriming(@Param("scp_id") int scp_id, @Param("personnel_id") int personnel_id);
}