package com.allianz.erpsystem.model.dto;

import lombok.Data;

@Data
public class CreateCustomerDTO {
    private String name;
    private String surname;
    private String tc;
    private String birthDate;
    private String address;
    private String phoneNumber;
}
