package com.allianz.erpsystem.model.dto;

import lombok.Data;

@Data
public class UpdateCustomerDTO {
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
}
