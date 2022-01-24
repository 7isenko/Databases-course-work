package io.github._7isenko.SCP1985.model.repositories;

import io.github._7isenko.SCP1985.model.entities.RetrievalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RetrievalEntityRepository extends JpaRepository<RetrievalEntity, Integer> {
    @Query(nativeQuery = true, value = "select * from retrieval where id = (select max(id) from retrieval) limit 1")
    RetrievalEntity findLast();
}