package com.allianz.erpsystem.repository;

import com.allianz.erpsystem.model.entity.CustomerEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM CustomerEntity c WHERE c.uuid = ?1")
    void deleteCustomerEntityByUuid(UUID uuid);

    CustomerEntity findByUuid(UUID uuid);
}
