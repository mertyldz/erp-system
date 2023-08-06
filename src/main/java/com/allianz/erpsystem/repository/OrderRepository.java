package com.allianz.erpsystem.repository;

import com.allianz.erpsystem.model.entity.OrderEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByUuid(UUID uuid);
    @Transactional
    void deleteByUuid(UUID uuid);
}
