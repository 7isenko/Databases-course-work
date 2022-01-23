package io.github._7isenko.SCP1985.model.repositories;

import io.github._7isenko.SCP1985.model.entities.ScpObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScpObjectEntityRepository extends JpaRepository<ScpObjectEntity, Integer> {
    @Query("select s from ScpObjectEntity s order by s.id")
    List<ScpObjectEntity> findAllOrderById();
}