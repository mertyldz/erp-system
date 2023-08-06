package com.allianz.erpsystem.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@EntityListeners({AuditingEntityListener.class})
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID uuid;

    @CreatedDate
    private Date creationDate;

    @LastModifiedDate
    private Date updateDate;

    @PrePersist
    protected void onCreate() {
        setUuid(java.util.UUID.randomUUID());
    }
}
