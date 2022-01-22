package io.github._7isenko.SCP1985.model.repositories;

import io.github._7isenko.SCP1985.model.entities.MobileGroupMembersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileGroupMembersEntityRepository extends JpaRepository<MobileGroupMembersEntity, Integer> {
}