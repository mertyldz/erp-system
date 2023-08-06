package com.allianz.erpsystem.repository;

import com.allianz.erpsystem.model.entity.BillEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface BillRepository extends JpaRepository<BillEntity, Long> {
    Optional<BillEntity> findBillEntityByUuid(UUID uuid);
    @Modifying
    @Transactional
    void deleteByUuid(UUID uuid);

}
