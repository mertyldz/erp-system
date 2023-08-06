package com.allianz.erpsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table
@AttributeOverride(name = "uuid", column = @Column(name = "order_uuid"))
@Getter
@Setter
public class OrderEntity extends BaseEntity {
    @Column
    private BigDecimal totalPriceTaxIncluded;

    @Column
    private String orderStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    @ToString.Exclude
    @JsonIgnore
    private CustomerEntity customerEntity;

//    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<ProductEntity> productEntityList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bill_id")
    @ToString.Exclude
    private BillEntity billEntity;

    @Override
    public String toString() {
        return "OrderEntity{" +
                "totalPriceTaxIncluded=" + totalPriceTaxIncluded +
                ", orderStatus='" + orderStatus + '\'' +
                ", customerEntity=" + customerEntity +
                '}';
    }
}
