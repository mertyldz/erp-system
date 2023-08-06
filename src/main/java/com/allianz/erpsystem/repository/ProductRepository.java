package com.allianz.erpsystem.repository;

import com.allianz.erpsystem.model.entity.ProductEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM ProductEntity p WHERE p.uuid = ?1")
    void deleteByUuid(UUID uuid);
    ProductEntity findProductEntityByUuid(UUID uuid);
}
