package io.github._7isenko.SCP1985.model.repositories;

import io.github._7isenko.SCP1985.model.entities.PersonnelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonnelEntityRepository extends JpaRepository<PersonnelEntity, Integer> {
    @Query("select p from PersonnelEntity p where concat(p.name, ' ', p.surname) = ?1")
    PersonnelEntity findPersonnelEntityByFullName(String name);
}