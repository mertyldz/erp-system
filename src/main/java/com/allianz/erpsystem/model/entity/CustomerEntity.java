package com.allianz.erpsystem.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@AttributeOverride(name = "uuid", column = @Column(name = "customer_uuid"))
@Getter
@Setter
public class CustomerEntity extends BaseEntity{
    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String tc;

    @Column
    private String birthDate;

    @Column
    private String address;

    @Column
    private String phoneNumber;

    @OneToMany(mappedBy = "customerEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<OrderEntity> orderEntityList;

    @OneToMany(mappedBy = "customerEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<BillEntity> billEntityList;

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", tc='" + tc + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
