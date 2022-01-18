package io.github._7isenko.SCP1985.jpa.repositories;

import io.github._7isenko.SCP1985.jpa.entities.AccessKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessKeyEntityRepository extends JpaRepository<AccessKeyEntity, String> {
}