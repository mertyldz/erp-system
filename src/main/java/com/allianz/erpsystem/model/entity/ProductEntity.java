package com.allianz.erpsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table
@AttributeOverride(name = "uuid", column = @Column(name = "product_uuid"))
@Getter
@Setter
public class ProductEntity extends BaseEntity{
    @Column
    private String name;
    @Column
    private BigDecimal price;
    @Column
    private Boolean isTaxIncluded;
    @Column
    private Integer stock;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "order_id")
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderEntity> orderEntity;

}
