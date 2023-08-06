package com.allianz.erpsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table
@AttributeOverride(name = "uuid", column = @Column(name = "bill_uuid"))
@Getter
@Setter
public class BillEntity extends BaseEntity {

    @Column
    private BigDecimal totalPrice;

    @Column
    private BigDecimal totalTaxFreePrice;

    @Column
    private BigDecimal totalTaxPrice;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    @JsonIgnore
    private OrderEntity orderEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    @ToString.Exclude
    @JsonIgnore
    CustomerEntity customerEntity;

    @Override
    public String toString() {
        return "BillEntity{" +
                ", totalPrice=" + totalPrice +
                ", totalTaxPrice=" + totalTaxPrice +
                '}';
    }
}
